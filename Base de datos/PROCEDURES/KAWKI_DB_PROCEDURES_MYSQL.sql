-- =====================================================
-- STORED PROCEDURES
-- =====================================================

USE KAWKI_DB;

DELIMITER $$
-- =====================================================
-- Obtener el comprobante de pago de una venta (solo si la venta es válida)
-- =====================================================
DROP PROCEDURE IF EXISTS SP_OBTENER_COMPROBANTE_POR_VENTA$$

CREATE PROCEDURE SP_OBTENER_COMPROBANTE_POR_VENTA(
    IN p_venta_id INT
)
BEGIN
    -- Retorna el comprobante solo si la venta asociada es válida
    SELECT 
        cp.COMPROBANTE_PAGO_ID,
        cp.FECHA_HORA_CREACION,
        cp.TIPO_COMPROBANTE_ID,
        cp.NUMERO_SERIE,
        cp.DNI_CLIENTE,
        cp.NOMBRE_CLIENTE,
        cp.RUC_CLIENTE,
        cp.RAZON_SOCIAL_CLIENTE,
        cp.DIRECCION_FISCAL_CLIENTE,
        cp.TELEFONO_CLIENTE,
        cp.TOTAL,
        cp.VENTA_ID,
        cp.METODO_PAGO_ID,
        cp.SUBTOTAL,
        cp.IGV
    FROM COMPROBANTES_PAGO cp
    INNER JOIN VENTAS v ON cp.VENTA_ID = v.VENTA_ID
    WHERE cp.VENTA_ID = p_venta_id 
      AND v.ES_VALIDA = 1;
END$$

DELIMITER ;

USE KAWKI_DB;

DELIMITER $$

-- =====================================================
-- LISTAR DETALLES DE VENTA POR VENTA_ID
-- =====================================================
DROP PROCEDURE IF EXISTS SP_LISTAR_DETALLES_POR_VENTA$$

CREATE PROCEDURE SP_LISTAR_DETALLES_POR_VENTA(
    IN p_venta_id INT
)
BEGIN
    SELECT 
        DETALLE_VENTA_ID,
        CANTIDAD,
        PRECIO_UNITARIO,
        SUBTOTAL,
        VENTA_ID,
        PROD_VARIANTE_ID
    FROM DETALLE_VENTAS
    WHERE VENTA_ID = p_venta_id;
END$$

DELIMITER ;

-- =====================================================
-- LISTAR VARIANTES DE PRODUCTO POR PRODUCTO_ID
-- =====================================================
USE KAWKI_DB;

DELIMITER $$

DROP PROCEDURE IF EXISTS SP_LISTAR_VARIANTES_POR_PRODUCTO$$

CREATE PROCEDURE SP_LISTAR_VARIANTES_POR_PRODUCTO(
    IN p_producto_id INT
)
BEGIN
    SELECT 
        PROD_VARIANTE_ID,
        SKU,
        STOCK,
        STOCK_MINIMO,
        ALERTA_STOCK,
        PRODUCTO_ID,
        COLOR_ID,
        TALLA_ID,
        URL_IMAGEN,
        FECHA_HORA_CREACION,
        DISPONIBLE,
        USUARIO_ID
    FROM PRODUCTOS_VARIANTES
    WHERE PRODUCTO_ID = p_producto_id;
END$$

DELIMITER ;

-- =====================================================
-- STORED PROCEDURES PARA USUARIOS
-- =====================================================

USE KAWKI_DB;

DELIMITER $$

-- =====================================================
-- 1. SP_VERIFICAR_UNICIDAD_USUARIO
-- Verifica si correo, nombre de usuario o DNI ya existen
-- Reutilizable para INSERT (usuarioIdExcluir = NULL) y UPDATE (usuarioIdExcluir = ID)
-- =====================================================
DROP PROCEDURE IF EXISTS SP_VERIFICAR_UNICIDAD_USUARIO$$

CREATE PROCEDURE SP_VERIFICAR_UNICIDAD_USUARIO(
    IN p_correo VARCHAR(100),
    IN p_nombre_usuario VARCHAR(50),
    IN p_dni VARCHAR(15),
    IN p_usuario_id_excluir INT,
    OUT p_correo_existe BOOLEAN,
    OUT p_usuario_existe BOOLEAN,
    OUT p_dni_existe BOOLEAN
)
BEGIN
    -- Inicializar variables de salida
    SET p_correo_existe = FALSE;
    SET p_usuario_existe = FALSE;
    SET p_dni_existe = FALSE;
    
    -- Verificar correo
    IF p_correo IS NOT NULL THEN
        IF p_usuario_id_excluir IS NULL THEN
            -- Para INSERT: buscar en todos los registros
            SELECT COUNT(*) > 0 INTO p_correo_existe
            FROM USUARIOS
            WHERE CORREO = p_correo;
        ELSE
            -- Para UPDATE: excluir el usuario actual
            SELECT COUNT(*) > 0 INTO p_correo_existe
            FROM USUARIOS
            WHERE CORREO = p_correo
            AND USUARIO_ID != p_usuario_id_excluir;
        END IF;
    END IF;
    
    -- Verificar nombre de usuario
    IF p_nombre_usuario IS NOT NULL THEN
        IF p_usuario_id_excluir IS NULL THEN
            SELECT COUNT(*) > 0 INTO p_usuario_existe
            FROM USUARIOS
            WHERE NOMBRE_USUARIO = p_nombre_usuario;
        ELSE
            SELECT COUNT(*) > 0 INTO p_usuario_existe
            FROM USUARIOS
            WHERE NOMBRE_USUARIO = p_nombre_usuario
            AND USUARIO_ID != p_usuario_id_excluir;
        END IF;
    END IF;
    
    -- Verificar DNI
    IF p_dni IS NOT NULL THEN
        IF p_usuario_id_excluir IS NULL THEN
            SELECT COUNT(*) > 0 INTO p_dni_existe
            FROM USUARIOS
            WHERE DNI = p_dni;
        ELSE
            SELECT COUNT(*) > 0 INTO p_dni_existe
            FROM USUARIOS
            WHERE DNI = p_dni
            AND USUARIO_ID != p_usuario_id_excluir;
        END IF;
    END IF;
END$$


-- =====================================================
-- 2. SP_LISTAR_USUARIOS_POR_TIPO
-- Lista usuarios filtrados por tipo de usuario
-- =====================================================
DROP PROCEDURE IF EXISTS SP_LISTAR_USUARIOS_POR_TIPO$$

CREATE PROCEDURE SP_LISTAR_USUARIOS_POR_TIPO(
    IN p_tipo_usuario_id INT
)
BEGIN
    SELECT 
        U.USUARIO_ID,
        U.NOMBRE,
        U.APE_PATERNO,
        U.DNI,
        U.TELEFONO,
        U.CORREO,
        U.NOMBRE_USUARIO,
        U.CONTRASENHA,
        U.FECHA_HORA_CREACION,
        U.TIPO_USUARIO_ID,
        U.ACTIVO
    FROM USUARIOS U
    WHERE U.TIPO_USUARIO_ID = p_tipo_usuario_id
    ORDER BY U.NOMBRE, U.APE_PATERNO;
END$$


-- =====================================================
-- 3. SP_CAMBIAR_CONTRASENHA
-- Cambia la contraseña de un usuario validando la actual
-- =====================================================
DROP PROCEDURE IF EXISTS SP_CAMBIAR_CONTRASENHA$$

CREATE PROCEDURE SP_CAMBIAR_CONTRASENHA(
    IN p_usuario_id INT,
    IN p_contrasenha_actual VARCHAR(255),
    IN p_contrasenha_nueva VARCHAR(255),
    OUT p_resultado INT,
    OUT p_mensaje VARCHAR(200)
)
BEGIN
    DECLARE v_contrasenha_bd VARCHAR(255);
    DECLARE v_usuario_existe INT;
    
    -- Códigos de resultado:
    -- 0 = Éxito
    -- 1 = Usuario no existe
    -- 2 = Contraseña actual incorrecta
    -- 3 = Nueva contraseña inválida (menos de 8 caracteres)
    
    -- Verificar que el usuario existe
    SELECT COUNT(*) INTO v_usuario_existe
    FROM USUARIOS
    WHERE USUARIO_ID = p_usuario_id;
    
    IF v_usuario_existe = 0 THEN
        SET p_resultado = 1;
        SET p_mensaje = 'Usuario no encontrado';
    ELSE
        -- Obtener contraseña actual de la BD
        SELECT CONTRASENHA INTO v_contrasenha_bd
        FROM USUARIOS
        WHERE USUARIO_ID = p_usuario_id;
        
        -- Verificar que la contraseña actual coincide
        IF v_contrasenha_bd != p_contrasenha_actual THEN
            SET p_resultado = 2;
            SET p_mensaje = 'La contraseña actual es incorrecta';
        ELSE
            -- Validar que la nueva contraseña tenga al menos 8 caracteres
            IF LENGTH(p_contrasenha_nueva) < 8 THEN
                SET p_resultado = 3;
                SET p_mensaje = 'La nueva contraseña debe tener al menos 8 caracteres';
            ELSE
                -- Actualizar la contraseña
                UPDATE USUARIOS
                SET CONTRASENHA = p_contrasenha_nueva
                WHERE USUARIO_ID = p_usuario_id;
                
                SET p_resultado = 0;
                SET p_mensaje = 'Contraseña actualizada correctamente';
            END IF;
        END IF;
    END IF;
END$$


-- =====================================================
-- 4. SP_AUTENTICAR_USUARIO
-- Autentica un usuario por nombre de usuario o correo
-- Retorna los datos del usuario si las credenciales son válidas
-- =====================================================
DROP PROCEDURE IF EXISTS SP_AUTENTICAR_USUARIO$$

CREATE PROCEDURE SP_AUTENTICAR_USUARIO(
    IN p_nombre_usuario_o_correo VARCHAR(100),
    IN p_contrasenha VARCHAR(255)
)
BEGIN
    SELECT 
        U.USUARIO_ID,
        U.NOMBRE,
        U.APE_PATERNO,
        U.DNI,
        U.TELEFONO,
        U.CORREO,
        U.NOMBRE_USUARIO,
        U.CONTRASENHA,
        U.FECHA_HORA_CREACION,
        U.TIPO_USUARIO_ID,
        U.ACTIVO
    FROM USUARIOS U
    WHERE (U.NOMBRE_USUARIO = p_nombre_usuario_o_correo 
           OR U.CORREO = p_nombre_usuario_o_correo)
    AND U.CONTRASENHA = p_contrasenha
    AND U.ACTIVO = 1
    LIMIT 1;
END$$

DELIMITER ;

-- =====================================================
-- STORED PROCEDURES PARA DESCUENTOS
-- =====================================================
USE KAWKI_DB;
DELIMITER $

-- =====================================================
-- 5. SP_LISTAR_DESCUENTOS_ACTIVOS
-- Lista descuentos que están marcados como activos
-- =====================================================
DROP PROCEDURE IF EXISTS SP_LISTAR_DESCUENTOS_ACTIVOS$

CREATE PROCEDURE SP_LISTAR_DESCUENTOS_ACTIVOS()
BEGIN
    SELECT 
        DESCUENTO_ID,
        DESCRIPCION,
        TIPO_CONDICION_ID,
        VALOR_CONDICION,
        TIPO_BENEFICIO_ID,
        VALOR_BENEFICIO,
        FECHA_INICIO,
        FECHA_FIN,
        ACTIVO
    FROM DESCUENTOS
    WHERE ACTIVO = 1
    ORDER BY FECHA_INICIO DESC;
END$

-- =====================================================
-- 6. SP_LISTAR_DESCUENTOS_VIGENTES
-- Lista descuentos activos y dentro del periodo de vigencia
-- =====================================================
DROP PROCEDURE IF EXISTS SP_LISTAR_DESCUENTOS_VIGENTES$

CREATE PROCEDURE SP_LISTAR_DESCUENTOS_VIGENTES()
BEGIN
    SELECT 
        DESCUENTO_ID,
        DESCRIPCION,
        TIPO_CONDICION_ID,
        VALOR_CONDICION,
        TIPO_BENEFICIO_ID,
        VALOR_BENEFICIO,
        FECHA_INICIO,
        FECHA_FIN,
        ACTIVO
    FROM DESCUENTOS
    WHERE ACTIVO = 1
    AND FECHA_INICIO <= NOW()
    AND FECHA_FIN >= NOW()
    ORDER BY FECHA_INICIO DESC;
END$

DELIMITER ;

-- PROCEDURE PARA COMPROBANTES_PAGO

USE KAWKI_DB;
DELIMITER $$

DROP PROCEDURE IF EXISTS SP_OBTENER_SIGUIENTE_NUMERO_SERIE$$

CREATE PROCEDURE SP_OBTENER_SIGUIENTE_NUMERO_SERIE(
    IN p_tipo_comprobante_id INT,
    OUT p_numero_serie VARCHAR(50)
)
BEGIN
    DECLARE v_prefijo VARCHAR(10);
    DECLARE v_contador INT;
    
    -- Determinar el prefijo según el tipo de comprobante
    IF p_tipo_comprobante_id = 3 THEN
        -- Factura
        SET v_prefijo = 'F001';
    ELSE
        -- Boleta (simple o con DNI)
        SET v_prefijo = 'B001';
    END IF;
    
    -- Contar cuántos comprobantes existen con ese prefijo
    SELECT COUNT(*) INTO v_contador
    FROM COMPROBANTES_PAGO
    WHERE NUMERO_SERIE LIKE CONCAT(v_prefijo, '-%');
    
    -- Generar el número de serie con formato: PREFIJO-XXXXXXXX (8 dígitos)
    SET p_numero_serie = CONCAT(v_prefijo, '-', LPAD(v_contador + 1, 8, '0'));
END$$

DELIMITER ;
-- ========================================================
-- Procedimientos almacenados para Productos - MySQL

USE KAWKI_DB;
DELIMITER $$

-- =====================================================
-- SP_VERIFICAR_STOCK_DISPONIBLE
-- Verifica si un producto tiene stock disponible en alguna de sus variantes
-- =====================================================
DROP PROCEDURE IF EXISTS SP_VERIFICAR_STOCK_DISPONIBLE$$

CREATE PROCEDURE SP_VERIFICAR_STOCK_DISPONIBLE(
    IN p_producto_id INT,
    OUT p_tiene_stock TINYINT
)
BEGIN
    DECLARE v_count INT;
    
    -- Contar cuántas variantes del producto tienen stock > 0
    SELECT COUNT(*) INTO v_count
    FROM PRODUCTOS_VARIANTES
    WHERE PRODUCTO_ID = p_producto_id
    AND STOCK > 0
    AND DISPONIBLE = 1;
    
    -- Si hay al menos una variante con stock, retornar 1, sino 0
    IF v_count > 0 THEN
        SET p_tiene_stock = 1;
    ELSE
        SET p_tiene_stock = 0;
    END IF;
END$$

-- =====================================================
-- SP_CALCULAR_STOCK_TOTAL
-- Calcula el stock total de un producto sumando todas sus variantes
-- =====================================================
DROP PROCEDURE IF EXISTS SP_CALCULAR_STOCK_TOTAL$$

CREATE PROCEDURE SP_CALCULAR_STOCK_TOTAL(
    IN p_producto_id INT,
    OUT p_stock_total INT
)
BEGIN
    -- Sumar el stock de todas las variantes del producto
    SELECT COALESCE(SUM(STOCK), 0) INTO p_stock_total
    FROM PRODUCTOS_VARIANTES
    WHERE PRODUCTO_ID = p_producto_id;
END$$

-- =====================================================
-- SP_LISTAR_PRODUCTOS_STOCK_BAJO
-- Lista productos que tienen al menos una variante con alerta de stock
-- =====================================================
DROP PROCEDURE IF EXISTS SP_LISTAR_PRODUCTOS_STOCK_BAJO$$

CREATE PROCEDURE SP_LISTAR_PRODUCTOS_STOCK_BAJO()
BEGIN
    SELECT DISTINCT 
        P.PRODUCTO_ID,
        P.DESCRIPCION,
        P.CATEGORIA_ID,
        P.ESTILO_ID,
        P.PRECIO_VENTA,
        P.FECHA_HORA_CREACION,
        P.USUARIO_ID
    FROM PRODUCTOS P
    INNER JOIN PRODUCTOS_VARIANTES PV ON P.PRODUCTO_ID = PV.PRODUCTO_ID
    WHERE PV.ALERTA_STOCK = 1
    ORDER BY P.PRODUCTO_ID;
END$$

DELIMITER ;

-- =====================================================
-- Stored Procedure: SP_EXISTE_VARIANTE 
-- Verifica si existe una variante con la combinación 
-- producto-color-talla especificada
-- =====================================================
USE KAWKI_DB;
DELIMITER $$

DROP PROCEDURE IF EXISTS SP_EXISTE_VARIANTE$$

CREATE PROCEDURE SP_EXISTE_VARIANTE(
    IN p_producto_id INT,
    IN p_color_id INT,
    IN p_talla_id INT,
    OUT p_existe TINYINT
)
BEGIN
    DECLARE v_count INT;
    
    -- Contar variantes que coincidan con la combinación
    SELECT COUNT(*) INTO v_count
    FROM PRODUCTOS_VARIANTES
    WHERE PRODUCTO_ID = p_producto_id
      AND COLOR_ID = p_color_id
      AND TALLA_ID = p_talla_id;
    
    -- Retornar 1 si existe, 0 si no existe
    SET p_existe = IF(v_count > 0, 1, 0);
END$$

DELIMITER ;

-- =====================================================
-- Stored Procedure: SP_LISTAR_PRODUCTOS_COMPLETO 
-- Lista todos los productos de manera completa sin variantes
-- =====================================================

USE KAWKI_DB;
DELIMITER $$

DROP PROCEDURE IF EXISTS SP_LISTAR_PRODUCTOS_COMPLETO$$

CREATE PROCEDURE SP_LISTAR_PRODUCTOS_COMPLETO()
BEGIN
    SELECT 
        -- Campos del producto
        p.PRODUCTO_ID,
        p.DESCRIPCION,
        p.PRECIO_VENTA,
        p.FECHA_HORA_CREACION,
        
        -- Categoría completa (JOIN)
        c.CATEGORIA_ID,
        c.NOMBRE AS CATEGORIA_NOMBRE,
        
        -- Estilo completo (JOIN)
        e.ESTILO_ID,
        e.NOMBRE AS ESTILO_NOMBRE,
        
        -- Usuario completo (JOIN)
        u.USUARIO_ID,
        u.NOMBRE_USUARIO
        
    FROM PRODUCTOS p
    INNER JOIN CATEGORIAS c ON p.CATEGORIA_ID = c.CATEGORIA_ID
    INNER JOIN ESTILOS e ON p.ESTILO_ID = e.ESTILO_ID
    INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    ORDER BY p.PRODUCTO_ID;
END$$

DELIMITER ;

-- =====================================================
-- Stored Procedure: SP_LISTAR_COMPROBANTES_COMPLETO 
-- Lista todos los comprobantes de pago
-- =====================================================

USE KAWKI_DB;
DELIMITER $$

DROP PROCEDURE IF EXISTS SP_LISTAR_COMPROBANTES_COMPLETO$$

CREATE PROCEDURE SP_LISTAR_COMPROBANTES_COMPLETO()
BEGIN
    SELECT 
        -- Campos del comprobante
        cp.COMPROBANTE_PAGO_ID,
        cp.FECHA_HORA_CREACION,
        cp.NUMERO_SERIE,
        cp.DNI_CLIENTE,
        cp.NOMBRE_CLIENTE,
        cp.RUC_CLIENTE,
        cp.RAZON_SOCIAL_CLIENTE,
        cp.DIRECCION_FISCAL_CLIENTE,
        cp.TELEFONO_CLIENTE,
        cp.TOTAL,
        cp.SUBTOTAL,
        cp.IGV,
        
        -- Tipo de comprobante completo (JOIN)
        tc.TIPO_COMPROBANTE_ID,
        tc.NOMBRE AS TIPO_COMPROBANTE_NOMBRE,
        
        -- Venta completa (JOIN) - SIN detalles, solo info básica
        v.VENTA_ID,
        v.FECHA_HORA_CREACION AS VENTA_FECHA_HORA,
        v.TOTAL AS VENTA_TOTAL,
        v.ES_VALIDA,
        
        -- Usuario de la venta (JOIN)
        u.USUARIO_ID,
        u.NOMBRE_USUARIO,
        
        -- Método de pago completo (JOIN)
        mp.METODO_PAGO_ID,
        mp.NOMBRE AS METODO_PAGO_NOMBRE
        
    FROM COMPROBANTES_PAGO cp
    INNER JOIN TIPOS_COMPROBANTE tc ON cp.TIPO_COMPROBANTE_ID = tc.TIPO_COMPROBANTE_ID
    INNER JOIN VENTAS v ON cp.VENTA_ID = v.VENTA_ID
    INNER JOIN USUARIOS u ON v.USUARIO_ID = u.USUARIO_ID
    INNER JOIN METODOS_PAGO mp ON cp.METODO_PAGO_ID = mp.METODO_PAGO_ID
    ORDER BY cp.COMPROBANTE_PAGO_ID DESC;
END$$

DELIMITER ;

-- =====================================================
-- Stored Procedure: SP_LISTAR_DESCUENTOS_COMPLETO 
-- Lista todos los descuentos con tipos de condición y beneficio
-- =====================================================
USE KAWKI_DB;

DELIMITER $$

DROP PROCEDURE IF EXISTS SP_LISTAR_DESCUENTOS_COMPLETO$$

CREATE PROCEDURE SP_LISTAR_DESCUENTOS_COMPLETO()
BEGIN
    SELECT 
        -- Campos del descuento
        d.DESCUENTO_ID,
        d.DESCRIPCION,
        d.VALOR_CONDICION,
        d.VALOR_BENEFICIO,
        d.FECHA_INICIO,
        d.FECHA_FIN,
        d.ACTIVO,
        
        -- Tipo de condición completo (JOIN)
        tc.TIPO_CONDICION_ID,
        tc.NOMBRE AS TIPO_CONDICION_NOMBRE,
        
        -- Tipo de beneficio completo (JOIN)
        tb.TIPO_BENEFICIO_ID,
        tb.NOMBRE AS TIPO_BENEFICIO_NOMBRE
        
    FROM DESCUENTOS d
    INNER JOIN TIPOS_CONDICION tc ON d.TIPO_CONDICION_ID = tc.TIPO_CONDICION_ID
    INNER JOIN TIPOS_BENEFICIO tb ON d.TIPO_BENEFICIO_ID = tb.TIPO_BENEFICIO_ID
    ORDER BY d.DESCUENTO_ID DESC;
END$$

DELIMITER ;