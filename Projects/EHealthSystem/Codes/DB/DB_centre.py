# https://realpython.com/python-csv/#what-is-a-csv-file : Reference site for csv (R,W)
# https://stackoverflow.com/questions/41468651/delete-a-row-from-csv-file : Reference site for csv (Edit)
# https://stackoverflow.com/questions/49370719/filenotfounderror-errno-2-no-such-file-or-directory-but-cant-use-exact-or : Reference site for relative path
# https://stackoverflow.com/questions/4383571/importing-files-from-different-folder : Reference site for How to import class from another directory

import csv ,shutil
import pickle

class DB_centre():
    def write_header(self):
        # 1. Write in csv
        with open("DB/centre.csv","w") as write_file_csv: 
            fieldnames = ['name','suburb','phone','rating','nratings','providers']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writeheader()
        
        with open("DB/centre.pickle","wb") as write_file_pkl:
            obj = {}
            obj["name"] = "name"
            obj["suburb"] = "suburb"
            obj["phone"] = "phone"
            obj["rating"] = "rating"
            obj["nratings"] = "nratings"
            obj["providers"] = "providers"
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
            
    def write(self,name,suburb,phone):        
        # 1. Write in pkl
        with open("DB/centre.pickle","ab") as write_file_pkl:
            obj = {}
            obj["name"] = name
            obj["suburb"] = suburb
            obj["phone"] = phone
            obj["rating"] = 0.0
            obj["nratings"] = 0
            obj["providers"] = []
            pickle.dump(obj,write_file_pkl)
            write_file_pkl.close()
        
        # 2. Write in csv
        with open("DB/centre.csv","a") as write_file_csv:
            fieldnames = ['name','suburb','phone','rating','nratings','providers']
            writer = csv.DictWriter(write_file_csv, fieldnames=fieldnames)
            writer.writerow({'name':name,'suburb':suburb,'phone':phone,'rating':0.0,'nratings':0,'providers':[]})
            
    
    def read(self):
        with open("DB/centre.pickle","rb") as read_file:
            lst = []
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['name'] != 'name':
                        lst.append(obj)
                except EOFError:
                    break
            
            return lst

    def delete(self,name):
        # 1. Delete in pkl
        with open("DB/centre.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    p = pickle.load(read_file)
                    if p['name'] != name:
                        pickle.dump(p,write_file)
                except EOFError:
                    break
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/centre.pickle')
        
        # 2. Delete in csv
        fieldnames = ['name','suburb','phone','rating','nratings','providers']
        with open("DB/centre.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for p in reader:
                if name != p['name']:
                    writer.writerow({'name':p['name'],'suburb':p['suburb'],'phone':p['phone'],'rating':p['rating'],'nratings':p['nratings'],'providers':p['providers']})
        
        shutil.move('DB/new.csv','DB/centre.csv')
        
    def update_rating(self,name,new_rating,nratings):
        # 1. Write in pkl
        with open("DB/centre.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['name'] != name:
                        pickle.dump(obj,write_file)
                    else:
                        obj['rating'] = new_rating
                        obj['nratings'] = nratings
                        pickle.dump(obj,write_file)
                except EOFError:
                    break    
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/centre.pickle')
        
        # 2. Write in csv
        fieldnames = ['name','suburb','phone','rating','nratings','providers']
        with open("DB/centre.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for p in reader:
                if name != p['name']:
                    writer.writerow({'name':p['name'],'suburb':p['suburb'],'phone':p['phone'],'rating':p['rating'],'nratings':p['nratings'],'providers':p['providers']})
                else:
                    writer.writerow({'name':p['name'],'suburb':p['suburb'],'phone':p['phone'],'rating':new_rating,'nratings':nratings,'providers':p['providers']})
                    
        shutil.move('DB/new.csv','DB/centre.csv')
    
    def add_provider(self,centre_name,provider):
        # 1. Write in pkl
        with open("DB/centre.pickle","rb") as read_file, open("DB/new.pickle","wb") as write_file:
            while 1:
                try:
                    obj = pickle.load(read_file)
                    if obj['name'] != centre_name:
                        pickle.dump(obj,write_file)
                    else:
                        obj['providers'].append(provider)
                        pickle.dump(obj,write_file)
                        provider_list = obj['providers']
                except EOFError:
                    break    
            write_file.close()
        
        shutil.move('DB/new.pickle','DB/centre.pickle')
             
        readable_list = []
        for a in provider_list:
            readable_list.append(a.name)
        
        # 2. Write in csv
        fieldnames = ['name','suburb','phone','rating','nratings','providers']
        with open("DB/centre.csv","r") as read_file, open("DB/new.csv","w") as write_file:
            reader = csv.DictReader(read_file, fieldnames=fieldnames)
            writer = csv.DictWriter(write_file,fieldnames=fieldnames)
            for p in reader:
                if centre_name != p['name']:
                    writer.writerow({'name':p['name'],'suburb':p['suburb'],'phone':p['phone'],'rating':p['rating'],'nratings':p['nratings'],'providers':p['providers']})
                else:
                    writer.writerow({'name':p['name'],'suburb':p['suburb'],'phone':p['phone'],'rating':p['rating'],'nratings':p['nratings'],'providers':readable_list})
                    
        shutil.move('DB/new.csv','DB/centre.csv')
