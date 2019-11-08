from classes.User import *

class Patient(User):
    def __init__(self, full_name, email, phone, password, medicare_no):
        User.__init__(self, full_name, email, phone, password)
        self._medicare_no = medicare_no
        self._patient_appointments = []
        
    @property
    def medicare_no(self):
        return self._medicare_no

    @property
    def get_appointments(self):
        return self._patient_appointments
   
    def add_appointment(self, appointment):
        self._patient_appointments.append(appointment)
