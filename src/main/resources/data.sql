INSERT INTO product (id, price, name, description, color, dress_type, dress_style, details, created_at)
VALUES
(1, 19.99, 'Classic T-Shirt', 'Soft cotton t-shirt.', 'Black', 'T_SHIRT', 'CASUAL', 'Comfortable for everyday wear', CURRENT_TIMESTAMP),
(2, 24.99, 'Premium Hoodie', 'Warm and cozy hoodie.', 'Gray', 'HOODIE', 'CASUAL', 'Perfect for the winter season', CURRENT_TIMESTAMP),
(3, 15.99, 'Basic Shorts', 'Comfy shorts for warm days.', 'Blue', 'T_SHIRT', 'GYM', 'Ideal for your workout routine', CURRENT_TIMESTAMP),
(4, 29.99, 'Graphic Tee', 'T-shirt with cool graphics.', 'White', 'T_SHIRT', 'CASUAL', 'Show off your style with a trendy graphic tee', CURRENT_TIMESTAMP),
(5, 49.99, 'Sports Hoodie', 'Breathable hoodie for workouts.', 'Red', 'HOODIE', 'GYM', 'Stay warm while you work out', CURRENT_TIMESTAMP),
(6, 19.99, 'Plain Black T-Shirt', 'Simple, classic, and stylish.', 'Black', 'T_SHIRT', 'CASUAL', 'Basic and essential piece in your wardrobe', CURRENT_TIMESTAMP),
(7, 34.99, 'Cotton Hoodie', 'Soft cotton hoodie with a large front pocket.', 'Navy', 'HOODIE', 'CASUAL', 'Cozy and stylish hoodie for everyday use', CURRENT_TIMESTAMP),
(8, 18.99, 'Athletic Shorts', 'Perfect for running and outdoor activities.', 'Gray', 'T_SHIRT', 'GYM', 'Lightweight and breathable for sports', CURRENT_TIMESTAMP),
(9, 27.99, 'Comfy Joggers', 'Perfect for lounging or gym.', 'Black', 'HOODIE', 'GYM', 'Soft and comfortable for gym or leisure', CURRENT_TIMESTAMP),
(10, 23.99, 'Fashion T-Shirt', 'Trendy and stylish fit.', 'Red', 'T_SHIRT', 'CASUAL', 'Add some color to your casual look', CURRENT_TIMESTAMP),
(11, 21.99, 'Cool Hoodie', 'Cool hoodie with a modern fit.', 'Purple', 'HOODIE', 'CASUAL', 'Perfect for everyday style', CURRENT_TIMESTAMP),
(12, 19.99, 'Zip Hoodie', 'Zip-up hoodie with a comfortable fit.', 'Gray', 'HOODIE', 'CASUAL', 'Perfect for a relaxed fit', CURRENT_TIMESTAMP),
(13, 14.99, 'Basic White Tee', 'Classic white t-shirt.', 'White', 'T_SHIRT', 'CASUAL', 'Essential item for every wardrobe', CURRENT_TIMESTAMP),
(14, 20.99, 'Summer Hoodie', 'Breathable hoodie for summer nights.', 'Beige', 'HOODIE', 'CASUAL', 'Stay comfortable on those cooler summer evenings', CURRENT_TIMESTAMP),
(15, 25.99, 'Tech T-Shirt', 'High-tech fabric for performance.', 'Black', 'T_SHIRT', 'GYM', 'Best for your intense workout sessions', CURRENT_TIMESTAMP),
(16, 35.99, 'Luxury Hoodie', 'Premium hoodie with high-end fabric.', 'Black', 'HOODIE', 'CASUAL', 'Feel luxurious every time you wear it', CURRENT_TIMESTAMP),
(17, 16.99, 'Performance Shorts', 'Durable and flexible shorts.', 'Blue', 'T_SHIRT', 'GYM', 'Great for intense workout activities', CURRENT_TIMESTAMP),
(18, 18.49, 'Stretchable Hoodie', 'Hoodie with stretchable fabric for comfort.', 'Gray', 'HOODIE', 'CASUAL', 'Perfect for both active and casual use', CURRENT_TIMESTAMP),
(19, 22.99, 'Vintage Tee', 'Retro t-shirt with a vintage print.', 'White', 'T_SHIRT', 'CASUAL', 'Bring back the old-school vibe with a vintage look', CURRENT_TIMESTAMP),
(20, 26.99, 'Outdoor Hoodie', 'Hoodie for all your outdoor adventures.', 'Camo', 'HOODIE', 'GYM', 'Best for outdoor activities and sports', CURRENT_TIMESTAMP);

INSERT INTO product_images (product_id, image_url)
VALUES
(1, 'https://cdn.pixabay.com/photo/2014/04/03/10/55/t-shirt-311732_1280.png'),
(1, 'https://cdn.pixabay.com/photo/2024/01/20/01/54/ai-generated-8520240_1280.jpg'),
(2, 'https://img.ltwebstatic.com/images3_pi/2021/07/26/1627293634a5a08447e3199b1b084bd29f2414d5f7_thumbnail_405x.jpg'),
(2, 'https://img.ltwebstatic.com/images3_pi/2021/07/26/16272936365b7e44d4947c8a14f5d97e7c58bcb42b_thumbnail_560x.webp'),
(2, 'https://img.ltwebstatic.com/images3_pi/2021/07/26/1627293638b3574386c0776337fdd3b1b069b1e66f_thumbnail_560x.webp'),
(3, 'https://img01.ztat.net/article/spp-media-p1/b1fc6fedca3c4fabbe377b41a094eef1/fbe0b796774546f6a75947f41083f37d.jpg?imwidth=1800&filter=packshot'),
(3, 'https://img01.ztat.net/article/spp-media-p1/d7de87a57e564d81996830d9f9861aba/7d0f248b9ae44772b5a7399a7ed185b9.jpg?imwidth=1800'),
(4, 'https://img01.ztat.net/article/spp-media-p1/00efde6b282e4ee6922da723ad4f9efa/04da3cef9ba3448cb3ff31c9f66702b2.jpg?imwidth=762&filter=packshot'),
(5, 'https://img01.ztat.net/article/spp-media-p1/b1100c08fa7142aaab953af65d6d5509/914bfaca4b7a4690a1e6d7e9e932bc1f.jpg?imwidth=1800&filter=packshot'),
(5, 'https://img01.ztat.net/article/spp-media-p1/f1200535c84a462eb3d12b60722711f2/a2c22d35b8c845b3967d645f3a11cc05.jpg?imwidth=1800'),
(6, 'https://img01.ztat.net/article/spp-media-p1/abc7057f5c8a401abc37ffddef2b0dfe/4ead2dae03674c148998654f71f2a7ba.jpg?imwidth=1800&filter=packshot'),
(6, 'https://img01.ztat.net/article/spp-media-p1/1ea0266274c547808479be9f1470f42d/973cf8be305d48d9a46d9b64e573ad6d.jpg?imwidth=1800'),
(7, 'https://img01.ztat.net/article/spp-media-p1/d8ab42412485444395d265d2ecfe6f28/9788a2f470f8465dadd15477128ad53e.jpg?imwidth=1800&filter=packshot'),
(7, 'https://img01.ztat.net/article/spp-media-p1/16d61182d3884eb19bd0acd55181f93b/78b2af803fb4464c8f61ff17804e1c0f.jpg?imwidth=1800'),
(8, 'https://img01.ztat.net/article/spp-media-p1/5376108ffb903dbaa46d8bbd2da55bb6/71087ce517374445827131dc1d8347a1.jpg?imwidth=1800'),
(8, 'https://img01.ztat.net/article/spp-media-p1/5c3fca6296683f11a7a021e6d91749f1/67c969fc425a4bbb86020fcf375106e8.jpg?imwidth=1800&filter=packshot'),
(9, 'https://img01.ztat.net/article/spp-media-p1/e4b6d816be6f4f289af6d7908a07d849/e540395433f0416d866b7171bc778ebe.jpg?imwidth=1800'),
(9, 'https://img01.ztat.net/article/spp-media-p1/35f71c1c86ad4a5c8e8b91931eee7bee/a8fa0f849cfd4afab62a2dd6cff470ae.jpg?imwidth=1800'),
(10, 'https://img01.ztat.net/article/spp-media-p1/55141613aec64cd3b2610ab717df8e7d/4acf7701e6f84560bb325c36ab8f50bc.jpg?imwidth=1800&filter=packshot'),
(10, 'https://img01.ztat.net/article/spp-media-p1/72726580f12845b4b864fd4a790d4a0c/f7236d180de94f3caca482c0967c1015.jpg?imwidth=1800');

INSERT INTO product_size_quantities (product_id, size, quantity)
VALUES
(1, 'S', 10),
(1, 'M', 15),
(1, 'L', 5),
(2, 'S', 20),
(2, 'M', 10),
(2, 'L', 15),
(3, 'S', 12),
(3, 'M', 18),
(3, 'L', 7),
(4, 'S', 25),
(4, 'M', 30),
(4, 'L', 10),
(5, 'S', 10),
(5, 'M', 12),
(5, 'L', 8),
(6, 'S', 15),
(6, 'M', 18),
(6, 'L', 10),
(7, 'S', 22),
(7, 'M', 28),
(7, 'L', 5),
(8, 'S', 30),
(8, 'M', 12),
(8, 'L', 10),
(9, 'S', 14),
(9, 'M', 16),
(9, 'L', 18),
(10, 'S', 15),
(10, 'M', 10),
(10, 'L', 25);

select * from cart;
select * from user;
select * from cart_item;
select * from product

delete from product;
delete from product_size_quantities;
delete from product_images;

delete from cart_item;
delete from cart;
delete from user;