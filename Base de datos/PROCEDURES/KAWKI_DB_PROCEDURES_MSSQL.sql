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