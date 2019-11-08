import csv,shutil
import pickle
from classes.Appointment import Appointment

class DB_consultation():
    def write_header(self):
        with open("DB/consultation.csv","w") as write_file_csv: 
            fieldnames = ['day','date','time','provider','centre','patient','reason','notes']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writeheader()
                        
        with open("DB/consultation.pickle","wb") as write_file_pkl:
            obj = Appointment("day","date","time","provider","centre","patient","reason")
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
            
    # You should use write_header first before start using write function.  
    def write(self,cst):
        # 1. Write in csv
        with open("DB/consultation.csv","a") as write_file_csv: 
            fieldnames = ['day','date','time','provider','centre','patient','reason','notes']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writerow({'day':cst.day,'date':cst.date,'time':cst.time,'provider':cst.provider.name,'centre':cst.centre.name,'patient':cst.patient.name,'reason':cst.reason,'notes':[]})
        # 2. Write in pkl
        with open("DB/consultation.pickle","ab") as write_file_pkl:
            pickle.dump(cst,write_file_pkl)
            write_file_pkl.close()

    def read(self):
        with open("DB/consultation.pickle","rb") as read_file:
            lst = []
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj.day != "day":
                        lst.append(obj)
                except EOFError:
                    break
            
            return lst
    
    def notes(self,date,time,provider,description):
        # 1. Write in pkl
        with open("DB/consultation.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj.date == date and obj.time == time and obj.provider == provider.id:
                        obj.notes = description
                        pickle.dump(obj,write_file)
                    else:
                        pickle.dump(obj,write_file)
                except EOFError:
                    break    
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/consultation.pickle')
        
        # 2. Write in csv
        fieldnames = ['day','date','time','provider','centre','patient','reason','notes']
        with open("DB/consultation.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for cst in reader:
                if cst['date'] == date and cst['time'] == time and cst['provider'] == provider:
                    writer.writerow({'day':cst['day'],'date':cst['date'],'time':cst['time'],'provider':cst['provider'],'centre':cst['centre'],'patient':cst['patient'],'reason':cst['reason'],'notes':description})
                else:
                    writer.writerow({'day':cst['day'],'date':cst['date'],'time':cst['time'],'provider':cst['provider'],'centre':cst['centre'],'patient':cst['patient'],'reason':cst['reason'],'notes':cst['notes']})
        
        shutil.move('DB/new.csv','DB/consultation.csv')
