from werkzeug.security import generate_password_hash, check_password_hash
from flask_login import UserMixin
 
class User(UserMixin):
    def __init__(self,name,id,phone,password):
        self._id = id
        self.set_password(password)
        self._name = name
        self._email = id
        self._phone = phone
        
    def set_password(self,password):
        self.pw_hash = generate_password_hash(password)
        
    def check_password(self, password):
        return check_password_hash(self.pw_hash, password)

    @property
    def id(self):
        return self._id
    
    @id.setter
    def id(self,id):
        self._id = id
        
    @property
    def name(self):
        return self._name
    
    @name.setter
    def name(self,name):
        self._name = name
    
    @property
    def email(self):
        return self._email
    
    @email.setter
    def email(self,email):
        self._email = email
        
    @property
    def phone(self):
        return self._phone
    
    @phone.setter
    def phone(self,phone):
        self._phone = phone
