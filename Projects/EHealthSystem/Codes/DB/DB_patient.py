# https://realpython.com/python-csv/#what-is-a-csv-file : Reference site for csv (R,W)
# https://stackoverflow.com/questions/41468651/delete-a-row-from-csv-file : Reference site for csv (Edit)
# https://stackoverflow.com/questions/49370719/filenotfounderror-errno-2-no-such-file-or-directory-but-cant-use-exact-or : Reference site for relative path
# https://stackoverflow.com/questions/4383571/importing-files-from-different-folder : Reference site for How to import class from another directory

import csv ,shutil
import pickle

class DB_patient():
    def write_header(self):
        with open("DB/patient.csv","w") as write_file_csv: 
            fieldnames = ['email', 'password', 'name','phone','medicare_no']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writeheader()
        with open("DB/patient.pickle","wb") as write_file_pkl:
            patient = {}
            patient["email"] = "email"
            patient["password"] = "password"
            patient["name"] = "name"
            patient["phone"] = "phone"
            patient["medicare_no"] = "medicare_no"
            pickle.dump(patient,write_file_pkl)
            write_file_pkl.close()
            
    # You should user write_header first before start using write function.        
    def write(self,email,password,name,phone,medicare_no):
        # 1. Write in csv
        with open("DB/patient.csv","a") as write_file_csv: 
            fieldnames = ['email', 'password', 'name','phone','medicare_no']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writerow({'email':email,'password':password,'name':name,'phone':phone,'medicare_no':medicare_no})
            
        # 2. Write in pkl
        with open("DB/patient.pickle","ab") as write_file_pkl:
            patient = {}
            patient["email"] = email
            patient["password"] = password
            patient["name"] = name
            patient["phone"] = phone
            patient["medicare_no"] = medicare_no
            pickle.dump(patient,write_file_pkl)
            write_file_pkl.close()
    
    def read(self):
        with open("DB/patient.pickle","rb") as read_file:
            patient_list = []
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['email'] != 'email':
                        patient_list.append(obj)
                except EOFError:
                    break
            
            return patient_list
    
