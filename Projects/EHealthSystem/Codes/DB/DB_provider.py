import csv ,shutil
import pickle

class DB_provider():
    def write_header(self):
        with open("DB/provider.csv","w") as write_file_csv:
            fieldnames = ['email','password','name','phone','provider_no','job','rating','nratings','centres']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writeheader()
            
        with open("DB/provider.pickle","wb") as write_file_pkl:
            obj = {}
            obj["email"] = "email"
            obj["password"] = "password"
            obj["name"] = "name"
            obj["phone"] = "phone"
            obj["provider_no"] = "provider_no"
            obj["job"] = "job"
            obj["rating"] = "rating"
            obj["nratings"] = "nratings"
            obj["centres"] = "centres"
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
            
    def write(self,email,password,name,phone,provider_no,job):
        # 1. Write in pkl
        with open("DB/provider.pickle","ab") as write_file_pkl:
            obj = {}
            obj["email"] = email
            obj["password"] = password
            obj["name"] = name
            obj["phone"] = phone
            obj["provider_no"] = provider_no
            obj["job"] = job
            obj["rating"] = 0.0
            obj["nratings"] = 0
            obj["centres"] = []
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
        
        # 2. Write in csv
        with open("DB/provider.csv","a") as write_file_csv:
            fieldnames = ['email','password','name','phone','provider_no','job','rating','nratings','centres']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writerow({'email':email,'password':password,'name':name,'phone':phone,'provider_no':provider_no,'job':job,'rating':0.0,'nratings':0,'centres':[]})

    def read(self):
        with open("DB/provider.pickle","rb") as read_file:
            lst = []
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['email'] != 'email':
                        lst.append(obj)
                except EOFError:
                    break
            
            return lst
        
    def add_centre(self,email,centre):
        # 1. Write in pkl
        with open("DB/provider.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['email'] != email:
                        pickle.dump(obj,write_file)
                    else:
                        obj['centres'].append(centre)
                        pickle.dump(obj,write_file)
                        centre_list = obj['centres']
                except EOFError:
                    break    
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/provider.pickle')
             
        readable_list = []
        for a in centre_list:
            readable_list.append(a.name)
        
        # 2. Write in csv
        fieldnames = ['email','password','name','phone','provider_no','job','rating','nratings','centres']
        with open("DB/provider.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for p in reader:
                if email != p['email']:
                    writer.writerow({'email':p['email'],'password':p['password'],'name':p['name'],'phone':p['phone'],'provider_no':p['provider_no'],'job':p['job'],'rating':p['rating'],'nratings':p['nratings'],'centres':p['centres']})
                else:
                    writer.writerow({'email':p['email'],'password':p['password'],'name':p['name'],'phone':p['phone'],'provider_no':p['provider_no'],'job':p['job'],'rating':p['rating'],'nratings':p['nratings'],'centres':readable_list})
        
        shutil.move('DB/new.csv','DB/provider.csv')
        
    def update_rating(self,email,new_rating,nratings):
        # 1. Write in pkl
        with open("DB/provider.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['email'] != email:
                        pickle.dump(obj,write_file)
                    else:
                        obj['rating'] = new_rating
                        obj['nratings'] = nratings
                        pickle.dump(obj,write_file)
                except EOFError:
                    break    
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/provider.pickle')
        
        # 2. Write in csv
        fieldnames = ['email','password','name','phone','provider_no','job','rating','nratings','centres']
        with open("DB/provider.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for p in reader:
                if email != p['email']:
                    writer.writerow({'email':p['email'],'password':p['password'],'name':p['name'],'phone':p['phone'],'provider_no':p['provider_no'],'job':p['job'],'rating':p['rating'],'nratings':p['nratings'],'centres':p['centres']})
                else:
                    writer.writerow({'email':p['email'],'password':p['password'],'name':p['name'],'phone':p['phone'],'provider_no':p['provider_no'],'job':p['job'],'rating':new_rating,'nratings':nratings,'centres':p['centres']})
        
        shutil.move('DB/new.csv','DB/provider.csv')
