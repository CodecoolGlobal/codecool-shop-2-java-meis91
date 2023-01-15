CREATE TABLE public.order
(
    id                  serial NOT NULL PRIMARY KEY,
    customer_id         int NOT NULL,
    order_date          DATE,
    total_price         DECIMAL,
    order_status        text
);

CREATE TABLE public.order_item
(
    id                  serial NOT NULL PRIMARY KEY,
    order_id            int,
    product_id          int,
    amount              int
);

ALTER TABLE ONLY public.order
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES public.customer (id);
ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES public.order (id),
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product (id);