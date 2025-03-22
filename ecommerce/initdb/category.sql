INSERT INTO `ecommerce`.`category` (`id`, `description`, `name`) VALUES ('1', 'men', 'Men');
INSERT INTO `ecommerce`.`category` (`id`, `description`, `name`) VALUES ('2', 'women', 'Women');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('3', '1', 'men clothing', 'Clothing');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('4', '2', 'women clothing', 'Clothing');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('5', '2', 'women accessories', 'Accessories');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('6', '2', 'women Brands', 'Brands');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('7', '1', 'men accessories', 'Accessories');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('8', '1', 'men brands', 'Brands');



-- women clothing:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('9', '4', 'women clothing tops', 'Tops');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('10', '4', 'women clothing dresses', 'Dresses');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('11', '4', 'women clothing pants', 'Pants');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('12', '4', 'women clothing denim', 'Denim');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('13', '4', 'women clothing sweaters', 'Sweaters');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('14', '4', 'women clothing t-shirts', 'T-Shirts');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('15', '4', 'women clothing jackets', 'Jackets');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('16', '4', 'women clothing activewear', 'Activewear');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('17', '4', 'women clothing browse all', 'Browse All');

-- women accessories:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('18', '5', 'women brand watches', 'Watches');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('19', '5', 'women accessories wallets', 'Wallets');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('20', '5', 'women accessories bags', 'Bags');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('21', '5', 'women accessories sunglasses', 'Sunglasses');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('22', '5', 'women accessories hats', 'Hats');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('23', '5', 'women accessories belts', 'Belts');

-- women brands:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('24', '6', 'women brand Full Nelson', 'Full Nelson');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('25', '6', 'women brand My Way', 'My Way');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('26', '6', 'women brand Re-Arranged', 'Re-Arranged');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('27', '6', 'women brand Counterfeit', 'Counterfeit');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('28', '6', 'women brand Significant Other', 'Significant Other');

-- men clothing:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('29', '3', 'men clothing tops', 'Tops');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('30', '3', 'men clothing shirts', 'Shirts');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('31', '3', 'men clothing pants', 'Pants');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('32', '3', 'men clothing denim', 'Denim');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('33', '3', 'men clothing sweaters', 'Sweaters');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('34', '3', 'men clothing t-shirts', 'T-Shirts');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('35', '3', 'men clothing jackets', 'Jackets');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('36', '3', 'men clothing activewear', 'Activewear');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('37', '3', 'men clothing browse all', 'Browse All');

-- men accessories:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('38', '7', 'men accessories watches', 'Watches');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('39', '7', 'men accessories wallets', 'Wallets');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('40', '7', 'men accessories bags', 'Bags');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('41', '7', 'men accessories sunglasses', 'Sunglasses');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('42', '7', 'men accessories hats', 'Hats');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('43', '7', 'men accessories belts', 'Belts');

-- men brands:
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('44', '8', 'men brand Full Nelson', 'Full Nelson');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('45', '8', 'men brand My Way', 'My Way');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('46', '8', 'men brand Re-Arranged', 'Re-Arranged');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('47', '8', 'men brand Counterfeit', 'Counterfeit');
INSERT INTO `ecommerce`.`category` (`id`, `parent_category_id`, `description`, `name`) VALUES ('48', '8', 'men brand Significant Other', 'Significant Other');