DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product
(
    id               serial  NOT NULL PRIMARY KEY,
    name             text    NOT NULL,
    description      text,
    default_price    decimal NOT NULL,
    default_currency text    NOT NULL,
    supplier_id      int     NOT NULL,
    category_id      int     NOT NULL
);

DROP TABLE IF EXISTS public.supplier;
CREATE TABLE public.supplier
(
    id          serial NOT NULL PRIMARY KEY,
    name        text   NOT NULL,
    description text

);
DROP TABLE IF EXISTS public.address CASCADE ;
CREATE TABLE public.address
(
    id      serial NOT NULL PRIMARY KEY,
    country text   NOT NULL,
    city    text   NOT NULL,
    zipcode text   NOT NULL,
    address text   NOT NULL
);

DROP TABLE IF EXISTS public.category;
CREATE TABLE public.category
(
    id          serial NOT NULL PRIMARY KEY,
    name        text   NOT NULL,
    description text,
    department  text
);
DROP TABLE IF EXISTS public.cart;

CREATE TABLE public.cart
(
    id          serial NOT NULL PRIMARY KEY,
    customer_id int    NOT NULL,
    products    int[]
);
DROP TABLE IF EXISTS public.customer;
CREATE TABLE public.customer
(
    id                  serial NOT NULL PRIMARY KEY,
    user_name           text   NOT NULL,
    email               text   NOT NULL,
    passwort            text   NOT NULL,
    cart_id             int    NOT NULL,
    billing_address_id  int    NOT NULL,
    shipping_address_id int    NOT NULL
);


ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES public.supplier (id);
ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES public.category (id);
ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fk_billing_address_id FOREIGN KEY (billing_address_id) REFERENCES public.address (id);
ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fk_shipping_address_id FOREIGN KEY (shipping_address_id) REFERENCES public.address (id);
ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES public.customer (id);