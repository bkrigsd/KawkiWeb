-- =====================================================
-- STORED PROCEDURES
-- =====================================================

USE KAWKI_DB;

DELIMITER $$

-- Obtener el comprobante de pago de una venta (solo si la venta es válida)
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
