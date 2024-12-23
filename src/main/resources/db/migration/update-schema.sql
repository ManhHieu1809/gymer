ALTER TABLE messages
DROP
FOREIGN KEY fk_conversation_message;

ALTER TABLE users
    ADD CONSTRAINT uc_users_user_name UNIQUE (user_name);

ALTER TABLE messages
DROP
COLUMN receiver_username;

ALTER TABLE messages
DROP
COLUMN sender_username;

ALTER TABLE messages
DROP
COLUMN conversation_id;

ALTER TABLE progress
DROP
COLUMN achievement;

ALTER TABLE progress
    ADD achievement VARCHAR(255) NULL;

ALTER TABLE nutritionplan
    MODIFY calo DOUBLE NULL;

ALTER TABLE conversation
    MODIFY conversation_id VARCHAR (255) NOT NULL;

ALTER TABLE messages
    ADD conversation_id INT NOT NULL;

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversation (id);

ALTER TABLE messages
    MODIFY conversation_id INT NOT NULL;

ALTER TABLE messages
    MODIFY is_read BIT (1) NOT NULL;

ALTER TABLE paymentmethod
    MODIFY method_name VARCHAR (255) NULL;

ALTER TABLE nutritionplan
    MODIFY name_nutrition_plan VARCHAR (255) NULL;

ALTER TABLE orders
    MODIFY order_date datetime NULL;

ALTER TABLE workoutplan
    MODIFY plan_name VARCHAR (255) NULL;

ALTER TABLE progress
    MODIFY planid INT NOT NULL;

ALTER TABLE product
    MODIFY product_name VARCHAR (255) NULL;

ALTER TABLE progress
    MODIFY progress_date date NULL;

ALTER TABLE orderdetail
    MODIFY quantity INT NULL;

ALTER TABLE product
    MODIFY quantity INT NOT NULL;

ALTER TABLE users
DROP
COLUMN roles;

ALTER TABLE users
    ADD roles VARCHAR(255) NULL;

ALTER TABLE orders
DROP
COLUMN statuss;

ALTER TABLE orders
    ADD statuss VARCHAR(255) NULL;

ALTER TABLE orders
    MODIFY statuss VARCHAR (255) NULL;

ALTER TABLE messages
    MODIFY timestamp datetime NOT NULL;

ALTER TABLE orders
    MODIFY total_price DOUBLE NOT NULL;

ALTER TABLE users
    MODIFY user_name VARCHAR (255) NULL;

ALTER TABLE users
    MODIFY user_password VARCHAR (255) NULL;

ALTER TABLE workout
    MODIFY workout_name VARCHAR (255) NULL;