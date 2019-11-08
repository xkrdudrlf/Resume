from classes.User import User
from flask_login import login_user
from classes.Patient import *
from classes.Provider import *

class UserManager():
    def __init__(self):
        self._users = []
        self._patients = []
        self._providers = []

    def add_patient(self,id,password,name,phone,medicare_no):
        new_patient = Patient(name,id,phone,password,medicare_no)
        for patient in self._patients:
            if patient.id == new_patient.id or patient.phone == new_patient.phone:
                return False
        self._patients.append(new_patient)
        self._users.append(new_patient)

    def get_patients(self):
        return self._patients

    def add_provider(self,id,password,name,phone,provider_no,job,rating,nratings,centres):

        if job == "GP":
            new_provider = GP(name,id,phone,password,provider_no,job,rating,nratings,centres)
        if job == "Pharmacist":
            new_provider = Pharmacist(name,id,phone,password,provider_no,job,rating,nratings,centres)
        if job == "Physio":
            new_provider = Physio(name,id,phone,password,provider_no,job,rating,nratings,centres)
        if job == "Pathologist":
            new_provider = Pathologist(name,id,phone,password,provider_no,job,rating,nratings,centres)

        for provider in self._providers:
            if provider.id == new_provider.id or provider.phone == new_provider.phone:
                return False
        self._providers.append(new_provider)
        self._users.append(new_provider)   

    def get_providers(self):
        return self._providers
        
    def get_user(self,id):
        for user in self._users:
            if user.id == id:
                return user
        return None
    
    def verify_user(self,id,password):
        for user in self._users:
            if user.id == id and user.check_password(password):
                login_user(user)
                return True
        return False
        
