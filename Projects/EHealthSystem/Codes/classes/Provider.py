from classes.User import *
from DB.DB_provider import *

class Provider(User):
    def __init__(self, full_name, email, phone, password, provider_no, job,rating=0.00,nratings=0,centres=[]):
        User.__init__(self, full_name, email, phone, password)
        self._provider_no = provider_no
        self._job = job
        self._rating = rating
        self._nratings = nratings          # number of ratings
        self._centres = centres
        self._consultations = []
   
    # add a centre to the provider
    def add_centre(self, centre):
        self._centres.append(centre)
        db_provider = DB_provider()
        db_provider.add_centre(self._email,centre)
        
    # update current rating
    def update_rating(self, new):
        current = self._rating
        n = self._nratings
        rating = (current + new)/(n + 1)
        self._rating = rating
        self._nratings = self._nratings + 1
        db_provider = DB_provider()
        db_provider.update_rating(self._email,self._rating,self._nratings)
        
    @property
    def email(self):
        return self._email    
    
    @property
    def phone(self):
        return self._phone
        
    @property
    def provider_no(self):
        return self._provider_no

    @property
    def job(self):
        return self._job

    @property
    def centres(self):
        return self._centres

    @property
    def rating(self):
        return self._rating
        
        
    @property
    def nratings(self):
        return self._nratings
        

    @property
    def get_consultations(self):
        return self._consultations
    
    # Choose 1 among 2
    '''
    def add_consultations(self, consultations_list):
        self._consultations.extend(consultations_list)
		db_provider.add_consultations(self._email,consultations_list)
	'''	
    def add_consultation(self, consultation):
        self._consultations.append(consultation)
        
class GP(Provider):
    def __init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres):
        Provider.__init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres)

class Pharmacist(Provider):
    def __init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres):
        Provider.__init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres)

class Physio(Provider):
    def __init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres):
        Provider.__init__(self, full_name, email, phone, password, provider_no, job,rating,nratings,centres)

class Pathologist(Provider):
    def __init__(self, full_name, email, phone, password, provider_no, job, rating,nratings,centres):
        Provider.__init__(self, full_name, email, phone, password, provider_no, job, rating,nratings,centres)
