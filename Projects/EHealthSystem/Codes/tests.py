from classes.UserManager import UserManager
from classes.AppointmentManager import AppointmentManager
from classes.CentreManager import CentreManager
from DB_loader import *
import unittest
import pytest
import datetime
import copy

patient = user_manager.get_user("jack@gmail.com")
provider = user_manager.get_user("anna@gmail.com")
centre = centre_manager.get_centre("UNSW Health Service")

'''
user_manager.verify_user(jack@gmail.com, cs1531)
user_manager.verify_user(toby@gmail.com, cs1531)
'''

class TestBooking():
    
    def test_patient_booking_success(self):
        day = "Friday"
        date = "2018-10-12"
        time = "10:00 AM"
        reason = "Check up"
        
        init_nappts = len(patient.get_appointments)
        init_nconsults = len(provider.get_consultations)
        init_ntotalappts = len(appointment_manager._bookings)
        init_ntotalconsults = len(appointment_manager._consultations)
        
        # test function is exact same as add_booking with DB calls removed
        appointment_manager.test_add_booking(day, date, time, provider, centre, patient, reason)
        
        assert len(patient.get_appointments) == init_nappts + 1
        assert len(provider.get_consultations) == init_nconsults + 1
        assert len(appointment_manager._bookings) == init_ntotalappts + 1
        assert len(appointment_manager._consultations) == init_ntotalconsults + 1
        
        # appointments inside patient class
        assert patient.get_appointments[init_nappts].day == day
        assert patient.get_appointments[init_nappts].date == date
        assert patient.get_appointments[init_nappts].time == time
        assert patient.get_appointments[init_nappts].reason == reason
        assert patient.get_appointments[init_nappts].provider == provider
        assert patient.get_appointments[init_nappts].centre == centre
        assert patient.get_appointments[init_nappts].patient == patient
        
        # consultations inside provider class
        assert provider.get_consultations[init_nconsults].day == day
        assert provider.get_consultations[init_nconsults].date == date
        assert provider.get_consultations[init_nconsults].time == time
        assert provider.get_consultations[init_nconsults].reason == reason
        assert provider.get_consultations[init_nconsults].provider == provider
        assert provider.get_consultations[init_nconsults].centre == centre
        assert provider.get_consultations[init_nconsults].patient == patient
        
        # bookings inside appointment manager
        assert appointment_manager._bookings[init_ntotalappts].day == day
        assert appointment_manager._bookings[init_ntotalappts].date == date
        assert appointment_manager._bookings[init_ntotalappts].time == time
        assert appointment_manager._bookings[init_ntotalappts].reason == reason
        assert appointment_manager._bookings[init_ntotalappts].provider == provider
        assert appointment_manager._bookings[init_ntotalappts].centre == centre
        assert appointment_manager._bookings[init_ntotalappts].patient == patient
        
        # consultations inside appointment manager
        assert appointment_manager._consultations[init_ntotalconsults].day == day
        assert appointment_manager._consultations[init_ntotalconsults].date == date
        assert appointment_manager._consultations[init_ntotalconsults].time == time
        assert appointment_manager._consultations[init_ntotalconsults].reason == reason
        assert appointment_manager._consultations[init_ntotalconsults].provider == provider
        assert appointment_manager._consultations[init_ntotalconsults].centre == centre
        assert appointment_manager._consultations[init_ntotalconsults].patient == patient
        
    def test_provider_booking_success(self):
        day = "Saturday"
        date = "2018/10/13"
        time = "9:00 AM"
        reason = ""
        
        init_nappts = len(patient.get_appointments)
        init_nconsults = len(provider.get_consultations)
        init_ntotalappts = len(appointment_manager._bookings)
        init_ntotalconsults = len(appointment_manager._consultations)
        
        appointment_manager.test_add_booking(day, date, time, provider, centre, patient, reason)
        
        assert len(patient.get_appointments) == init_nappts + 1
        assert len(provider.get_consultations) == init_nconsults + 1
        assert len(appointment_manager._bookings) == init_ntotalappts + 1
        assert len(appointment_manager._consultations) == init_ntotalconsults + 1
        
        assert patient.get_appointments[init_nappts].day == day
        assert patient.get_appointments[init_nappts].date == date
        assert patient.get_appointments[init_nappts].time == time
        assert patient.get_appointments[init_nappts].reason == reason
        assert patient.get_appointments[init_nappts].provider == provider
        assert patient.get_appointments[init_nappts].centre == centre
        assert patient.get_appointments[init_nappts].patient == patient
        
        assert provider.get_consultations[init_nconsults].day == day
        assert provider.get_consultations[init_nconsults].date == date
        assert provider.get_consultations[init_nconsults].time == time
        assert provider.get_consultations[init_nconsults].reason == reason
        assert provider.get_consultations[init_nconsults].provider == provider
        assert provider.get_consultations[init_nconsults].centre == centre
        assert provider.get_consultations[init_nconsults].patient == patient
        
        assert appointment_manager._bookings[init_ntotalappts].day == day
        assert appointment_manager._bookings[init_ntotalappts].date == date
        assert appointment_manager._bookings[init_ntotalappts].time == time
        assert appointment_manager._bookings[init_ntotalappts].reason == reason
        assert appointment_manager._bookings[init_ntotalappts].provider == provider
        assert appointment_manager._bookings[init_ntotalappts].centre == centre
        assert appointment_manager._bookings[init_ntotalappts].patient == patient
        
        assert appointment_manager._consultations[init_ntotalconsults].day == day
        assert appointment_manager._consultations[init_ntotalconsults].date == date
        assert appointment_manager._consultations[init_ntotalconsults].time == time
        assert appointment_manager._consultations[init_ntotalconsults].reason == reason
        assert appointment_manager._consultations[init_ntotalconsults].provider == provider
        assert appointment_manager._consultations[init_ntotalconsults].centre == centre
        assert appointment_manager._consultations[init_ntotalconsults].patient == patient
        
class TestViewHistory():    
             
    def test_patient_history_success(self):  
        past_day = "Thursday"
        past_date = "2018-10-12"
        time = "9:00 AM"
        reason = "Check up"
        
        future_day = "Thursday"
        future_date = "2018-10-19"
        time = "9:00 AM"
        reason = "Check up"
            
        #Initialise dummy
        dummy_app_manager = DummyAppointmentManager();      
        init_nappts = len(patient.get_appointments)
        init_nconsults = len(provider.get_consultations)
        init_ntotalappts = len(appointment_manager._bookings)
        init_ntotalconsults = len(appointment_manager._consultations)
        
        #Initialise current date and time
        date_current = datetime.datetime.now().date()
        date_current = datetime.datetime(date_current.year, date_current.month, date_current.day)
        
        # Add both appointments to the patient
        dummy_app_manager.add_booking(past_day, past_date, time, provider, centre, patient, reason)
        dummy_app_manager.add_booking(future_day, future_date, time, provider, centre, patient, reason)
        
        
        bookings = dummy_app_manager.get_bookings(patient)
               
        #Use the logic used in routes.py to see intialise history
        i = 0
        while i < len(bookings):
            date_bk = datetime.datetime.strptime(bookings[i].date, '%Y-%m-%d')
            if date_bk >= date_current:
                bookings.remove(bookings[i])
                i = i - 1
            i = i + 1
        
        #Test history
        i = 0
        while i < len(bookings):
            date_bk = datetime.datetime.strptime(bookings[i].date, '%Y-%m-%d')
            assert date_bk<=date_current
            assert bookings[i].reason == reason
            
            #Make sure patient and provider are the same (have the same history)
            assert bookings[i].patient == patient
            assert bookings[i].provider == provider
            i+=1
            
                
class TestManageHistory():
    pass
    
    
#Make dummy appointment manager to test history
class DummyAppointmentManager(AppointmentManager):
    def __init__(self):
        self._bookings = []
        self._consultations = []
	
	# Makes a booking
    def add_booking(self, day, date, time, provider, centre, patient, reason=""):
        new_appointment = Booking(day, date, time, provider, centre, patient, reason)
        self._bookings.append(new_appointment)
        patient.add_appointment(new_appointment)        
        new_consultation = Consultation(day, date, time, provider, centre, patient, reason)
        self._consultations.append(new_consultation)
        provider.add_consultation(new_consultation)

    # Load AppointmentDB /Consultation DB
    def load_appointment(self, day, date, time, provider, centre, patient, reason=""):
        new_appointment = Booking(day, date, time, provider, centre, patient, reason)
        self._bookings.append(new_appointment)
        patient.add_appointment(new_appointment)
        
     # Returns all the appointments booked by a patient
    def get_bookings(self, patient):
        patient_bookings = []
        for booking in self._bookings:
            if patient == booking._patient:
                patient_bookings.append(booking)
        return patient_bookings
    
