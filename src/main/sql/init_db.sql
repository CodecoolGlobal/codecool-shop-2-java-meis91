DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product
(
    id               INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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
    customer_id int,
    products    int[]
);
DROP TABLE IF EXISTS public.customer;
CREATE TABLE public.customer
(
    id                  serial NOT NULL PRIMARY KEY,
    email               text   NOT NULL UNIQUE,
    password            text,
    salt                bytea,
    phone_number        text,
    cart_id             int,
    billing_address_id  int,
    shipping_address_id int
);

INSERT INTO supplier (name, description)
VALUES ('Amazon', 'Digital content and services'),
       ('Lenovo', 'Computers'),
       ('Dell', 'Computers'),
       ('Appel', 'Computers');

INSERT INTO category (name, department, description)
VALUES ('Tablet','Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.'),
       ('Laptop','Hardware', 'A foldable computer, with keyboard.'),
       ('PC','Hardware', 'A classic computer, with keyboard.');

INSERT INTO product (name, description, default_price, default_currency, supplier_id, category_id)
VALUES ('Amazon Fire','No description yet', '49.9', 'USD', '1', '1'),
       ('Lenovo IdeaPad Miix 700','No description yet', '479', 'USD', '2', '1'),
       ('Amazon Fire HD 8', 'Amazons latest Fire HD 8 tablet is a great value for media consumption', '89', 'USD', '1', '1' ),
       ('Dell XPS 13','No description yet', '1298', 'USD', '3', '2'),
       ('Mac Pro','No description yet', '6499', 'USD', '4', '3');



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