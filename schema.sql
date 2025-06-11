-- Enable UUID generation if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Define ENUM types
CREATE TYPE estado_solicitud_pago AS ENUM ('PENDIENTE', 'PAGADO', 'EXPIRADO', 'FALLIDO');
CREATE TYPE estado_pago AS ENUM ('PROCESANDO', 'EXITOSO', 'FALLIDO', 'REEMBOLSADO', 'PARCIALMENTE_REEMBOLSADO');
CREATE TYPE estado_comprobante AS ENUM ('GENERADO', 'ENVIADO', 'ANULADO', 'FALLO_ENVIO');

-- Table: usuarios
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL DEFAULT 'USUARIO',
    esta_activo BOOLEAN NOT NULL DEFAULT TRUE,
    ultimo_login_en TIMESTAMP WITH TIME ZONE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: solicitudes_pago
CREATE TABLE solicitudes_pago (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    monto DECIMAL(15, 2) NOT NULL,
    moneda VARCHAR(3) NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    estado estado_solicitud_pago NOT NULL DEFAULT 'PENDIENTE',
    qr_identificador_unico VARCHAR(255) UNIQUE NOT NULL,
    fecha_expiracion TIMESTAMP WITH TIME ZONE,
    creado_por_usuario_id UUID REFERENCES usuarios(id) ON DELETE SET NULL,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: pagos
CREATE TABLE pagos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    solicitud_pago_id UUID REFERENCES solicitudes_pago(id) NOT NULL,
    transaccion_gateway_id VARCHAR(255) UNIQUE,
    fecha_pago TIMESTAMP WITH TIME ZONE NOT NULL,
    monto_pagado DECIMAL(15, 2) NOT NULL,
    moneda_pagada VARCHAR(3) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    datos_metodo_pago JSONB,
    estado estado_pago NOT NULL,
    referencia_pago_externa VARCHAR(255),
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: comprobantes
CREATE TABLE comprobantes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pago_id UUID UNIQUE NOT NULL REFERENCES pagos(id) ON DELETE CASCADE,
    numero_comprobante VARCHAR(50) UNIQUE NOT NULL,
    fecha_emision TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    monto_total DECIMAL(15, 2) NOT NULL,
    moneda VARCHAR(3) NOT NULL,
    monto_en_palabras VARCHAR(500),
    concepto_general TEXT,
    emisor_datos JSONB NOT NULL,
    receptor_datos JSONB NOT NULL,
    ruta_pdf VARCHAR(255),
    url_visualizacion_comprobante VARCHAR(255),
    firma_digital_cfdi TEXT,
    qr_validacion_comprobante_datos TEXT,
    estado estado_comprobante NOT NULL DEFAULT 'GENERADO',
    creado_por_usuario_id UUID REFERENCES usuarios(id) ON DELETE SET NULL,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: comprobante_items
CREATE TABLE comprobante_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    comprobante_id UUID NOT NULL REFERENCES comprobantes(id) ON DELETE CASCADE,
    producto_sku VARCHAR(100),
    descripcion VARCHAR(500) NOT NULL,
    cantidad DECIMAL(15, 4) NOT NULL DEFAULT 1.0000,
    unidad_medida VARCHAR(50) DEFAULT 'unidad',
    precio_unitario_bruto DECIMAL(15, 2) NOT NULL,
    descuento_linea DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    subtotal_antes_impuestos DECIMAL(15, 2) NOT NULL,
    impuestos_aplicados JSONB,
    monto_total_impuestos_linea DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    total_linea DECIMAL(15, 2) NOT NULL,
    notas TEXT,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add triggers for actualizado_en timestamps (example for one table, repeat for others)
-- This is a common pattern but the exact syntax can vary (e.g., if using an ORM, it might handle this)
CREATE OR REPLACE FUNCTION update_actualizado_en_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.actualizado_en = NOW();
   RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_usuarios_actualizado_en
BEFORE UPDATE ON usuarios
FOR EACH ROW
EXECUTE FUNCTION update_actualizado_en_column();

CREATE TRIGGER update_solicitudes_pago_actualizado_en
BEFORE UPDATE ON solicitudes_pago
FOR EACH ROW
EXECUTE FUNCTION update_actualizado_en_column();

CREATE TRIGGER update_pagos_actualizado_en
BEFORE UPDATE ON pagos
FOR EACH ROW
EXECUTE FUNCTION update_actualizado_en_column();

CREATE TRIGGER update_comprobantes_actualizado_en
BEFORE UPDATE ON comprobantes
FOR EACH ROW
EXECUTE FUNCTION update_actualizado_en_column();

CREATE TRIGGER update_comprobante_items_actualizado_en
BEFORE UPDATE ON comprobante_items
FOR EACH ROW
EXECUTE FUNCTION update_actualizado_en_column();
