from classes.Centre import *
from classes.User import *
from classes.Provider import *

#Use fuzzywuzzy module to help with search
from fuzzywuzzy import fuzz
from fuzzywuzzy import process


##SORT BASED ON NAME in alphabetical order##

#Only use fuzzywuzzy for searches when results[] is empty - otherwise wrong items may be returned because they are classified as partial searches
#Use fuzz.partial_ratio for everything with a single word - maybe use 60
#Use fuzz.token_set_ratio for everything with multiple words - 

#def search_providers(suburb,service,provider_name,providers):
def search_providers(suburb,service,provider_name,providers):  
    results = []
    
    #Defining ratio - Ratio number for successful partial search
    defining_ratio = 70
    
    #Set up checker for each of the possible searches
    suburb_check = False
    service_check = False
    provider_name_check = False
    
    #Criteria counter
    criteria_counter = 0
    
    #Check if checker is true or false
    if (suburb is not ""):
        suburb_check = True
        criteria_counter +=1
    if (service is not ""):
        service_check = True
        criteria_counter +=1
    if (provider_name is not ""):
        provider_name_check = True
        criteria_counter +=1
        
    

    """Set up queries
    suburb_query = ["randwick","kensington","sydney","darlington","ultimo"]
    service_query = ["pathologist","gp","physiotherapist","pharmacist"]    
    provider_name_query = ["toby","gary","samuel","sid","michael","anna","thomas","ian"]"""
    
    #Make all arguments lowercase
    suburb = suburb.lower()
    service = service.lower()
    provider_name = provider_name.lower()
    
    
    if provider_name_check:
        for items in providers:
            if provider_name in items._name:
                results.append(items)
            else:
                if (fuzz.partial_ratio(provider_name,items._name.lower())) > defining_ratio:
                    results.append(items)
                                                       
                
    if service_check:
        for items in providers:
            if service in items._job: 
                results.append(items)
            else:
                if (fuzz.partial_ratio(service,items._job.lower())) > defining_ratio:
                     results.append(items)

    if suburb_check:
        for items in providers:
            for centres in items._centres:
                if suburb in centres.suburb:
                    results.append(items)
                else:
                    if (fuzz.partial_ratio(suburb,centres.suburb.lower())) > defining_ratio:
                        results.append(items)
                        
    results = check_duplicates(results,criteria_counter)
    results = sort_now(results)
    return results

       
#def search_centres(centre_name,suburb,centres):
def search_centres(centre_name,suburb,centres):   
    results = []
    
    #Set up defining ratio
    defining_ratio = 70

    #Initialise checker for each of the possible cases
    centre_name_check = False
    suburb_check = False

    #Set up criteria counter
    criteria_counter = 0

    #Assign values to checker
    if (centre_name is not ""):  
        centre_name_check = True
        criteria_counter +=1
    if (suburb is not ""):
        suburb_check = True 
        criteria_counter +=1
    
    #Make arguments lowercase
    centre_name = centre_name.lower()
    suburb = suburb.lower() 
    
    if centre_name_check:  
        for items in centres:
            if centre_name in items.name:
                results.append(items)
            else:
                if (fuzz.partial_ratio(centre_name,items._name.lower())) > defining_ratio:
                    results.append(items)
    
        
    if suburb_check:
        for items in centres:
            if suburb in items.suburb:
                results.append(items)
            else:
                if (fuzz.partial_ratio(suburb,items._suburb.lower())) > defining_ratio:
                        results.append(items)

    results = check_duplicates(results,criteria_counter)
    results = sort_now(results)
    
    return results
    

def check_duplicates(results,criteria_counter):
    to_return = []
    for items in results:
        if results.count(items) == criteria_counter:
            to_return.append(items)
    
    return list(set(to_return))
    
def sort_now(results):
    unsorted=True
    
    while unsorted:
        unsorted = False
        for element in range(0,len(results)-1):
                if results[element]._name >= results[element + 1]._name:
                    hold = results[element + 1]
                    results[element + 1] = results[element]
                    results[element] = hold
                    unsorted = True      # this was moved up from the else block
                
    return results
             
