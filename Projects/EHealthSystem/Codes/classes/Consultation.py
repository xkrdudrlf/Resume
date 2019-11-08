from classes.Appointment import *
from DB.DB_consultation import *

class Consultation(Appointment):
    def __init__(self, day, date, time, provider, centre, patient,reason, notes=""):
        Appointment.__init__(self, day, date, time, provider, centre, patient, reason)
        self._notes = notes
		

    @property
    def notes(self):
        return self._notes
	
    @notes.setter
    def notes(self, description):
        self._notes = description
        db_consultation = DB_consultation()
        db_consultation.notes(self._date,self._time,self._provider,description)
    
    @property
    def time(self):
        return self._time
    
    @property
    def date(self):
        return self._date

    @property
    def patient(self):
        return self._patient
    
    @property
    def centre(self):
        return self._centre
    