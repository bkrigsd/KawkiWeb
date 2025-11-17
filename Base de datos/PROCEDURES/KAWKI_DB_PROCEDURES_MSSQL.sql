-- =====================================================
-- STORED PROCEDURES
-- =====================================================

USE KAWKI_DB;
GO

-- =====================================================
-- Obtener el comprobante de pago de una venta (solo si la venta es válida)
-- =====================================================
IF OBJECT_ID('SP_OBTENER_COMPROBANTE_POR_VENTA', 'P') IS NOT NULL
    DROP PROCEDURE SP_OBTENER_COMPROBANTE_POR_VENTA;
GO

CREATE PROCEDURE SP_OBTENER_COMPROBANTE_POR_VENTA
    @p_venta_id INT
AS
BEGIN
    SET NOCOUNT ON;
    
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
    WHERE cp.VENTA_ID = @p_venta_id 
      AND v.ES_VALIDA = 1;
END
GO
-- ===================================================================
USE KAWKI_DB;
GO

-- =====================================================
-- LISTAR DETALLES DE VENTA POR VENTA_ID
-- =====================================================
IF OBJECT_ID('SP_LISTAR_DETALLES_POR_VENTA', 'P') IS NOT NULL
    DROP PROCEDURE SP_LISTAR_DETALLES_POR_VENTA;
GO

CREATE PROCEDURE SP_LISTAR_DETALLES_POR_VENTA
    @p_venta_id INT
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        DETALLE_VENTA_ID,
        CANTIDAD,
        PRECIO_UNITARIO,
        SUBTOTAL,
        VENTA_ID,
        PROD_VARIANTE_ID
    FROM DETALLE_VENTAS
    WHERE VENTA_ID = @p_venta_id;
END
GO

USE KAWKI_DB;
GO

-- =====================================================
-- LISTAR VARIANTES DE PRODUCTO POR PRODUCTO_ID
-- =====================================================
IF OBJECT_ID('SP_LISTAR_VARIANTES_POR_PRODUCTO', 'P') IS NOT NULL
    DROP PROCEDURE SP_LISTAR_VARIANTES_POR_PRODUCTO;
GO

CREATE PROCEDURE SP_LISTAR_VARIANTES_POR_PRODUCTO
    @p_producto_id INT
AS
BEGIN
    SET NOCOUNT ON;
    
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
    WHERE PRODUCTO_ID = @p_producto_id;
END
GO

-- =====================================================
-- STORED PROCEDURES PARA USUARIOS
-- =====================================================

USE KAWKI_DB;
GO

-- =====================================================
-- 1. SP_VERIFICAR_UNICIDAD_USUARIO
-- Verifica si correo, nombre de usuario o DNI ya existen
-- Reutilizable para INSERT (usuarioIdExcluir = NULL) y UPDATE (usuarioIdExcluir = ID)
-- =====================================================
IF OBJECT_ID('dbo.SP_VERIFICAR_UNICIDAD_USUARIO', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_VERIFICAR_UNICIDAD_USUARIO;
GO

CREATE PROCEDURE dbo.SP_VERIFICAR_UNICIDAD_USUARIO
    @p_correo VARCHAR(100),
    @p_nombre_usuario VARCHAR(50),
    @p_dni VARCHAR(15),
    @p_usuario_id_excluir INT = NULL,
    @p_correo_existe BIT OUTPUT,
    @p_usuario_existe BIT OUTPUT,
    @p_dni_existe BIT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Inicializar variables de salida
    SET @p_correo_existe = 0;
    SET @p_usuario_existe = 0;
    SET @p_dni_existe = 0;
    
    DECLARE @count INT;
    
    -- Verificar correo
    IF @p_correo IS NOT NULL
    BEGIN
        IF @p_usuario_id_excluir IS NULL
        BEGIN
            -- Para INSERT: buscar en todos los registros
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE CORREO = @p_correo;
        END
        ELSE
        BEGIN
            -- Para UPDATE: excluir el usuario actual
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE CORREO = @p_correo
            AND USUARIO_ID != @p_usuario_id_excluir;
        END
        
        IF @count > 0
            SET @p_correo_existe = 1;
    END
    
    -- Verificar nombre de usuario
    IF @p_nombre_usuario IS NOT NULL
    BEGIN
        IF @p_usuario_id_excluir IS NULL
        BEGIN
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE NOMBRE_USUARIO = @p_nombre_usuario;
        END
        ELSE
        BEGIN
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE NOMBRE_USUARIO = @p_nombre_usuario
            AND USUARIO_ID != @p_usuario_id_excluir;
        END
        
        IF @count > 0
            SET @p_usuario_existe = 1;
    END
    
    -- Verificar DNI
    IF @p_dni IS NOT NULL
    BEGIN
        IF @p_usuario_id_excluir IS NULL
        BEGIN
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE DNI = @p_dni;
        END
        ELSE
        BEGIN
            SELECT @count = COUNT(*)
            FROM USUARIOS
            WHERE DNI = @p_dni
            AND USUARIO_ID != @p_usuario_id_excluir;
        END
        
        IF @count > 0
            SET @p_dni_existe = 1;
    END
END
GO


-- =====================================================
-- 2. SP_LISTAR_USUARIOS_POR_TIPO
-- Lista usuarios filtrados por tipo de usuario
-- =====================================================
IF OBJECT_ID('dbo.SP_LISTAR_USUARIOS_POR_TIPO', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_LISTAR_USUARIOS_POR_TIPO;
GO

CREATE PROCEDURE dbo.SP_LISTAR_USUARIOS_POR_TIPO
    @p_tipo_usuario_id INT
AS
BEGIN
    SET NOCOUNT ON;
    
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
    WHERE U.TIPO_USUARIO_ID = @p_tipo_usuario_id
    ORDER BY U.NOMBRE, U.APE_PATERNO;
END
GO


-- =====================================================
-- 3. SP_CAMBIAR_CONTRASENHA
-- Cambia la contraseña de un usuario validando la actual
-- =====================================================
IF OBJECT_ID('dbo.SP_CAMBIAR_CONTRASENHA', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_CAMBIAR_CONTRASENHA;
GO

CREATE PROCEDURE dbo.SP_CAMBIAR_CONTRASENHA
    @p_usuario_id INT,
    @p_contrasenha_actual VARCHAR(255),
    @p_contrasenha_nueva VARCHAR(255),
    @p_resultado INT OUTPUT,
    @p_mensaje VARCHAR(200) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @v_contrasenha_bd VARCHAR(255);
    DECLARE @v_usuario_existe INT;
    
    -- Códigos de resultado:
    -- 0 = Éxito
    -- 1 = Usuario no existe
    -- 2 = Contraseña actual incorrecta
    -- 3 = Nueva contraseña inválida (menos de 8 caracteres)
    
    -- Verificar que el usuario existe
    SELECT @v_usuario_existe = COUNT(*)
    FROM USUARIOS
    WHERE USUARIO_ID = @p_usuario_id;
    
    IF @v_usuario_existe = 0
    BEGIN
        SET @p_resultado = 1;
        SET @p_mensaje = 'Usuario no encontrado';
        RETURN;
    END
    
    -- Obtener contraseña actual de la BD
    SELECT @v_contrasenha_bd = CONTRASENHA
    FROM USUARIOS
    WHERE USUARIO_ID = @p_usuario_id;
    
    -- Verificar que la contraseña actual coincide
    IF @v_contrasenha_bd != @p_contrasenha_actual
    BEGIN
        SET @p_resultado = 2;
        SET @p_mensaje = 'La contraseña actual es incorrecta';
        RETURN;
    END
    
    -- Validar que la nueva contraseña tenga al menos 8 caracteres
    IF LEN(@p_contrasenha_nueva) < 8
    BEGIN
        SET @p_resultado = 3;
        SET @p_mensaje = 'La nueva contraseña debe tener al menos 8 caracteres';
        RETURN;
    END
    
    -- Actualizar la contraseña
    UPDATE USUARIOS
    SET CONTRASENHA = @p_contrasenha_nueva
    WHERE USUARIO_ID = @p_usuario_id;
    
    SET @p_resultado = 0;
    SET @p_mensaje = 'Contraseña actualizada correctamente';
END
GO


-- =====================================================
-- 4. SP_AUTENTICAR_USUARIO
-- Autentica un usuario por nombre de usuario o correo
-- Retorna los datos del usuario si las credenciales son válidas
-- =====================================================
IF OBJECT_ID('dbo.SP_AUTENTICAR_USUARIO', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_AUTENTICAR_USUARIO;
GO

CREATE PROCEDURE dbo.SP_AUTENTICAR_USUARIO
    @p_nombre_usuario_o_correo VARCHAR(100),
    @p_contrasenha VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT TOP 1
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
    WHERE (U.NOMBRE_USUARIO = @p_nombre_usuario_o_correo 
           OR U.CORREO = @p_nombre_usuario_o_correo)
    AND U.CONTRASENHA = @p_contrasenha
    AND U.ACTIVO = 1;
END
GO

-- =====================================================
-- STORED PROCEDURES PARA DESCUENTOS 
-- =====================================================
USE KAWKI_DB;
GO
-- =====================================================
-- 5. SP_LISTAR_DESCUENTOS_ACTIVOS
-- Lista descuentos que están marcados como activos
-- =====================================================
IF OBJECT_ID('dbo.SP_LISTAR_DESCUENTOS_ACTIVOS', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_LISTAR_DESCUENTOS_ACTIVOS;
GO

CREATE PROCEDURE dbo.SP_LISTAR_DESCUENTOS_ACTIVOS
AS
BEGIN
    SET NOCOUNT ON;
    
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
END
GO


-- =====================================================
-- 6. SP_LISTAR_DESCUENTOS_VIGENTES
-- Lista descuentos activos y dentro del periodo de vigencia
-- =====================================================
IF OBJECT_ID('dbo.SP_LISTAR_DESCUENTOS_VIGENTES', 'P') IS NOT NULL
    DROP PROCEDURE dbo.SP_LISTAR_DESCUENTOS_VIGENTES;
GO

CREATE PROCEDURE dbo.SP_LISTAR_DESCUENTOS_VIGENTES
AS
BEGIN
    SET NOCOUNT ON;
    
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
    AND FECHA_INICIO <= GETDATE()
    AND FECHA_FIN >= GETDATE()
    ORDER BY FECHA_INICIO DESC;
END
GO

-- Genera el siguiente número de serie correlativo para comprobantes

USE KAWKI_DB;
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SP_OBTENER_SIGUIENTE_NUMERO_SERIE]') AND type in (N'P', N'PC'))
    DROP PROCEDURE [dbo].[SP_OBTENER_SIGUIENTE_NUMERO_SERIE]
GO

CREATE PROCEDURE [dbo].[SP_OBTENER_SIGUIENTE_NUMERO_SERIE]
    @p_tipo_comprobante_id INT,
    @p_numero_serie VARCHAR(50) OUTPUT
AS
BEGIN
    DECLARE @v_prefijo VARCHAR(10);
    DECLARE @v_contador INT;
    
    -- Determinar el prefijo según el tipo de comprobante
    IF @p_tipo_comprobante_id = 3
    BEGIN
        -- Factura
        SET @v_prefijo = 'F001';
    END
    ELSE
    BEGIN
        -- Boleta (simple o con DNI)
        SET @v_prefijo = 'B001';
    END
    
    -- Contar cuántos comprobantes existen con ese prefijo
    SELECT @v_contador = COUNT(*)
    FROM COMPROBANTES_PAGO
    WHERE NUMERO_SERIE LIKE @v_prefijo + '-%';
    
    -- Generar el número de serie con formato: PREFIJO-XXXXXXXX (8 dígitos)
    SET @p_numero_serie = @v_prefijo + '-' + RIGHT('00000000' + CAST(@v_contador + 1 AS VARCHAR), 8);
END
GO