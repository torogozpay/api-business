ALTER TABLE IF EXISTS public.venta
    ADD COLUMN apply_split boolean;

ALTER TABLE IF EXISTS public.venta
    ADD COLUMN amount_msat numeric(15, 6);

ALTER TABLE IF EXISTS public.venta
    ADD COLUMN fecha_creacion timestamp(6) without time zone;

ALTER TABLE IF EXISTS public.venta
    ADD COLUMN fecha_modificacion timestamp(6) without time zone;

ALTER TABLE IF EXISTS public.venta
    ADD COLUMN distributed boolean;