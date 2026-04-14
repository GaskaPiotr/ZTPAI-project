ALTER TABLE app_user
ADD CONSTRAINT fk_app_user_role
FOREIGN KEY (role_id)
REFERENCES user_role(id);