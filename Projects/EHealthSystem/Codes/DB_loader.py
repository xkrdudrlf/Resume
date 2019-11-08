from DB.DB_patient import DB_patient
from DB.DB_provider import DB_provider
from DB.DB_centre import DB_centre
from DB.DB_appointment import DB_appointment
from DB.DB_consultation import DB_consultation
from classes.UserManager import *
from classes.CentreManager import *
from classes.AppointmentManager import *

user_manager = UserManager()
centre_manager = CentreManager()
appointment_manager = AppointmentManager()

# Load Patient DB
db_paitent = DB_patient()
patient_list = db_paitent.read()
for p in patient_list:
    user_manager.add_patient(p['email'],p['password'],p['name'],p['phone'],p['medicare_no'])

# Load Centre DB
db_centre = DB_centre()
centre_list = db_centre.read()
for c in centre_list:
    centre_manager.add_centre(c['name'],c['suburb'],c['phone'],c['rating'],c['nratings'],c['providers'])

# Load Provider DB
db_provider = DB_provider()
provider_list = db_provider.read()
for pro in provider_list:
    user_manager.add_provider(pro['email'],pro['password'],pro['name'],pro['phone'],pro['provider_no'],pro['job'],pro['rating'],pro['nratings'],pro['centres'])

# Load Appointment DB
db_appointment = DB_appointment()
appointment_list = db_appointment.read()

for apo in appointment_list:
    patient = user_manager.get_user(apo.patient.id)
    provider = user_manager.get_user(apo.provider.id)
    centre = centre_manager.get_centre(apo.centre.name)
    appointment_manager.load_appointment(apo.day,apo.date,apo.time,provider,centre,patient,apo.reason)
        
# Load Consultation DB
db_consultation = DB_consultation()
consultation_list = db_consultation.read()

for con in consultation_list:
    patient = user_manager.get_user(con.patient.id)    
    provider = user_manager.get_user(con.provider.id)
    centre = centre_manager.get_centre(con.centre.name)
    appointment_manager.load_consultation(con.day,con.date,con.time,provider,centre,patient,con.reason)

