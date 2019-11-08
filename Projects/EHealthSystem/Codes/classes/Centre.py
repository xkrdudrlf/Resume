from abc import ABC
from classes.Provider import *
from DB.DB_centre import *

class Centre(ABC):
    def __init__(self, name, suburb, phone,rating,nratings,providers):
        self._name = name
        self._suburb = suburb
        self._phone = phone
        self._rating = rating
        self._nratings = nratings   # Number of ratings
        self._providers = providers

    def update_rating(self, new):
        current = self._rating
        n = self._nratings
        rating = (current + new)/(n + 1)
        self._rating = rating
        self._nratings = self._nratings + 1
        db_centre = DB_centre()
        db_centre.update_rating(self._name,self._rating,self._nratings)
        
    def add_provider(self, provider):
        self._providers.append(provider)
        db_centre = DB_centre()
        db_centre.add_provider(self._name,provider)
        
    @property
    def providers(self):
        return self._providers

    @property
    def name(self):
        return self._name

    @property
    def suburb(self):
        return self._suburb

    @property
    def phone(self):
        return self._phone

    @property
    def rating(self):
        return self._rating
        
    @property
    def nratings(self):
        return self._nratings

class Hospital(Centre):
    def __init__(self, name, suburb, phone,rating,nratings,providers):
        Centre.__init__(self, name, suburb, phone,rating,nratings,providers)

class MedicalCentre(Centre):
    def __init__(self, name, suburb, phone,rating,nratings,providers):
        Centre.__init__(self, name, suburb, phone,rating,nratings,providers)
