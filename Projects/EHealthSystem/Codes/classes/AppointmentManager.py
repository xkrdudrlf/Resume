from classes.Appointment import *
from classes.Booking import *
from classes.Consultation import *
from DB.DB_appointment import *
from DB.DB_consultation import *

'''from classes.Patient import *
from classes.Provider import *'''

class AppointmentManager():
    def __init__(self):
        self._bookings = []
        self._consultations = []
	
	# Makes a booking
    def add_booking(self, day, date, time, provider, centre, patient, reason=""):
        new_appointment = Booking(day, date, time, provider, centre, patient, reason)
        self._bookings.append(new_appointment)
        patient.add_appointment(new_appointment)
        db_appointment = DB_appointment()
        db_appointment.write(new_appointment)
        
        new_consultation = Consultation(day, date, time, provider, centre, patient, reason)
        self._consultations.append(new_consultation)
        provider.add_consultation(new_consultation)
        db_consultation = DB_consultation()
        db_consultation.write(new_consultation)
    
    # Load AppointmentDB /Consultation DB
    def load_appointment(self, day, date, time, provider, centre, patient, reason=""):
        new_appointment = Booking(day, date, time, provider, centre, patient, reason)
        self._bookings.append(new_appointment)
        patient.add_appointment(new_appointment)
        
    def load_consultation(self, day, date, time, provider, centre, patient, reason=""):
        new_consultation = Consultation(day, date, time, provider, centre, patient, reason)
        self._consultations.append(new_consultation)
        provider.add_consultation(new_consultation)
    
    # Returns all the appointments booked by a patient
    def get_bookings(self, patient):
        patient_bookings = []
        for booking in self._bookings:
            if patient == booking._patient:
                patient_bookings.append(booking)
        return patient_bookings

    # Returns all the consultations for a provider
    def get_consultations(self, provider):
        provider_consultations = []
        for consult in self._consultations:
            if provider == consult.provider:
                provider_consultations.append(consult)
        return provider_consultations

    def get_consultation(self, date, time):
        for consult in self._consultations:
            if consult.date == date and consult.time == time:
                return consult
        return None
        
    # Returns a list containing times booked for a date with the provider
    def get_times(self, date, provider):
        consultations = self.get_consultations(provider)
        times = []
        for consults in consultations:
            if consults.date == date:
                times.append(consults.time)
        return times
       
     # Returns an appointment on a particular day
    def get_booking(self, patient, date):
        bookings = self.get_bookings(patient)
        for bk in bookings:
            if bk.date == date:
                return bk
	
	# Returns all the appointments booked on a particular date			
    def date_appointments(self, date):
        appointments = []
        for ap in self._bookings:
            if ap.date == date:
                appointments.append(ap)
        return appointments
	
    def test_add_booking(self, day, date, time, provider, centre, patient, reason=""):
	    new_appointment = Booking(day, date, time, provider, centre, patient, reason)
	    self._bookings.append(new_appointment)
	    patient.add_appointment(new_appointment)
        #db_appointment = DB_appointment()
        #db_appointment.write(new_appointment)
        
	    new_consultation = Consultation(day, date, time, provider, centre, patient, reason)
	    self._consultations.append(new_consultation)
	    provider.add_consultation(new_consultation)
        #db_consultation = DB_consultation()
        #db_consultation.write(new_consultation)
	
