import csv
import pickle
from classes.Appointment import Appointment

class DB_appointment():
    def write_header(self):
        with open("DB/appointment.csv","w") as write_file_csv: 
            fieldnames = ['day','date','time','provider','centre','patient','reason']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writeheader()
            
        with open("DB/appointment.pickle","wb") as write_file_pkl:
            obj = Appointment("day","date","time","provider","centre","patient","reason")
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
            
    # You should use write_header first before start using write function.  
    def write(self,appointment):
        # 1. Write in csv
        with open("DB/appointment.csv","a") as write_file_csv: 
            fieldnames = ['day','date','time','provider','centre','patient','reason']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writerow({'day':appointment.day,'date':appointment.date,'time':appointment.time,'provider':appointment.provider.name,'centre':appointment.centre.name,'patient':appointment.patient.name,'reason':appointment.reason})
        # 2. Write in pkl
        with open("DB/appointment.pickle","ab") as write_file_pkl:
            pickle.dump(appointment,write_file_pkl)
            write_file_pkl.close()

    def read(self):
        with open("DB/appointment.pickle","rb") as read_file:
            lst = []
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj.day != "day":
                        lst.append(obj)
                except EOFError:
                    break
            
            return lst
