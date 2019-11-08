from classes.Appointment import *

class Booking(Appointment):
    def __init__(self, day, date, time, provider, centre, patient, reason=""):
        Appointment.__init__(self, day, date, time, provider, centre, patient, reason)

