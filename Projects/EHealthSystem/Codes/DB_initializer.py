from classes.User import *
from classes.UserManager import *
from classes.Patient import *
from classes.Provider import *
from classes.Centre import *
from classes.CentreManager import *
from classes.AppointmentManager import *

from DB.DB_patient import DB_patient
from DB.DB_provider import DB_provider
from DB.DB_centre import DB_centre
from DB.DB_appointment import DB_appointment
from DB.DB_consultation import DB_consultation

user_manager = UserManager()
centre_manager = CentreManager()
appointment_manager = AppointmentManager()

# NOTICE (Younggil Tak,06/10/18)                                               
# - Use for the first time only to create an initial database(csv,pkl files) 

# Initialize Appointment DB
db_appointment = DB_appointment()
db_appointment.write_header()

# Initialize Consultation DB
db_consultation = DB_consultation()
db_consultation.write_header()
    
# ADD Patients 
db_patient = DB_patient()
db_patient.write_header()
db_patient.write('jack@gmail.com','cs1531','Jack','92165432','10010')
db_patient.write("tom@gmail.com","abcd1234","Tom","93114521","10011")
db_patient.write("isaac@gmail.com","@5D&DrwC","Isaac","94192365","10012")
db_patient.write("hao@gmail.com","Nh3Tu;", "Hao","91923491", "10013")

# LOAD Patient DB
db_paitent = DB_patient()
patient_list = db_paitent.read()
for p in patient_list:
    user_manager.add_patient(p['email'],p['password'],p['name'],p['phone'],p['medicare_no'])

##############################################################################################################

# ADD Centres
db_centre = DB_centre()
db_centre.write_header()
db_centre.write("Sydney Children Hospital", "Randwick", "93821111")
db_centre.write("UNSW Health Service", "Kensington", "93855425")
db_centre.write("Prince of Wales Hospital", "Randwick", "93821118")
db_centre.write("Royal Prince Alfred Hospital", "Sydney", "95156111")
db_centre.write("USYD Health Service", "Darlington", "93513484")
db_centre.write("UTS Health Service", "Ultimo", "95141177")

# LOAD Centre DB
db_centre = DB_centre()
centre_list = db_centre.read()
for c in centre_list:
    centre_manager.add_centre(c['name'],c['suburb'],c['phone'],c['rating'],c['nratings'],c['providers'])

##############################################################################################################

# ADD Providers
db_provider = DB_provider()
db_provider.write_header()
db_provider.write("toby@gmail.com","cs1531","Toby","94286451","01","Pathologist")
db_provider.write("gary@gmail.com","xyz914","Gary","94694117","02","GP")
db_provider.write("samuel@gmail.com","password","Samuel","90860825","03","Pathologist")
db_provider.write("sid@gmail.com","comp@1531","Sid", "92574113","04","Physio")
db_provider.write("michael@gmail.com","7G445p*n@P","Michael", "96220714","05","GP")
db_provider.write("anna@gmail.com","This is a very long password","Anna", "96220715","06","GP")
db_provider.write("thomas@gmail.com","k_q3nG","Thomas", "91432561","07","Pharmacist")
db_provider.write("ian@gmail.com","x`8&-)6(K","Ian", "94156326","08","Physio")

# LOAD Provider DB
db_provider = DB_provider()
provider_list = db_provider.read()
for pro in provider_list:
    user_manager.add_provider(pro['email'],pro['password'],pro['name'],pro['phone'],pro['provider_no'],pro['job'],pro['rating'],pro['nratings'],pro['centres'])

##############################################################################################################

# INITIALIZE Provider.centres
user = user_manager.get_user("toby@gmail.com")
centre = centre_manager.get_centre("Sydney Children Hospital")
user.add_centre(centre)
centre = centre_manager.get_centre("UNSW Health Service")
user.add_centre(centre)

user = user_manager.get_user("gary@gmail.com")
centre = centre_manager.get_centre("Sydney Children Hospital")
user.add_centre(centre)
centre = centre_manager.get_centre("USYD Health Service")
user.add_centre(centre)

user = user_manager.get_user("samuel@gmail.com")
centre = centre_manager.get_centre("Prince of Wales Hospital")
user.add_centre(centre)

user = user_manager.get_user("sid@gmail.com")
centre = centre_manager.get_centre("Royal Prince Alfred Hospital")
user.add_centre(centre)

user = user_manager.get_user("michael@gmail.com")
centre = centre_manager.get_centre("USYD Health Service")
user.add_centre(centre)

user = user_manager.get_user("anna@gmail.com")
centre = centre_manager.get_centre("UTS Health Service")
user.add_centre(centre)
centre = centre_manager.get_centre("Prince of Wales Hospital")
user.add_centre(centre)
centre = centre_manager.get_centre("Sydney Children Hospital")
user.add_centre(centre)

user = user_manager.get_user("thomas@gmail.com")
centre = centre_manager.get_centre("Prince of Wales Hospital")
user.add_centre(centre)

user = user_manager.get_user("ian@gmail.com")
centre = centre_manager.get_centre("Sydney Children Hospital")
user.add_centre(centre)

##############################################################################################################

# INITIALIZE Centre.providers
centre = centre_manager.get_centre("Sydney Children Hospital")
user = user_manager.get_user("gary@gmail.com")
centre.add_provider(user)
user = user_manager.get_user("ian@gmail.com")
centre.add_provider(user)
user = user_manager.get_user("anna@gmail.com")
centre.add_provider(user)

centre = centre_manager.get_centre("UNSW Health Service")
user = user_manager.get_user("toby@gmail.com")
centre.add_provider(user)

centre = centre_manager.get_centre("Prince of Wales Hospital")
user = user_manager.get_user("samuel@gmail.com")
centre.add_provider(user)
user = user_manager.get_user("anna@gmail.com")
centre.add_provider(user)
user = user_manager.get_user("thomas@gmail.com")
centre.add_provider(user)

centre = centre_manager.get_centre("Royal Prince Alfred Hospital")
user = user_manager.get_user("sid@gmail.com")
centre.add_provider(user)

centre = centre_manager.get_centre("USYD Health Service")
user = user_manager.get_user("gary@gmail.com")
centre.add_provider(user)
user = user_manager.get_user("michael@gmail.com")
centre.add_provider(user)

centre = centre_manager.get_centre("UTS Health Service")
user = user_manager.get_user("anna@gmail.com")
centre.add_provider(user)

##############################################################################################################

# For Debugging
'''
patient_list = db_paitent.read()
for p in patient_list:
    print(p)

provider_list = db_provider.read()
for p in provider_list:
    print(p)

center_list = db_center.read()
for c in center_list:
    print(p)
'''
