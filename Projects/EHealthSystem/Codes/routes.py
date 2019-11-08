from flask import Flask, redirect, request, render_template, url_for
from flask_login import LoginManager,login_user, current_user, login_required, logout_user
from classes.User import User
from classes.UserManager import UserManager 
from server import app,login_manager
# from main import *
from search import search_providers,search_centres, sort_now
from DB_loader import *
import datetime
import copy

'''
u = user_manager.get_user("Jack")
apps = appointment_manager.get_appointments(user)
for a in apps:
    print(type(a).__name__)
'''
@login_manager.user_loader
def load_user(user_id):
    user = user_manager.get_user(user_id)
    if user:
        return user
    return False 

# LOGIN/LOGOUT ROUTES
@app.route('/',methods=["GET","POST"])
def login():
    login_fail = 0
    if request.method == "POST":
        user_id = request.form["username"]
        password = request.form["password"] 
        
        if user_manager.verify_user(user_id,password):
            User = user_manager.get_user(user_id)
            return redirect(url_for('homepage',user_id=user_id))
        else:
            login_fail = 1
    return render_template("login.html",login = login_fail) 

 
@app.route('/logout')
def logout():
    logout_user()
    return redirect(url_for('login')) 

# HOMEPAGE / HEALTH CENTER / HEALTH PROVIDER / VIEW BOOKING ROUTES
# --------------YOU CAN DELETE THESE COMMENT, ONCE YOU GET THIS --------------------------------
#
## 1. WHAT IS "REQUEST.ARGS.GET('') ?
## - IF YOU GET SOME VALUE FROM URL_FOR, THEN YOU CAN GET THAT VALUE USING "REQUEST.ARGS.GET('VALUENAME')
##  (BUT, YOU CAN'T PASS USER ITSELF, I RECKON THE PASSED VALUE IS RECOGNIZED AS A STRING BUT USER IS A CLASS NOT A SIMPLE STRING)
##
## 2. HOW CAN I ACCESS TO USER'S ATTRIBUTES ?
## - YOU MIGHT NEED TO USE USER'S ATTRIBUTES IN YOUR HTML.
##      EX) YOU NEED LOGGED IN USER'S NAME IN HOMEPAGE.HTMAL
## - 1. FIRST GET USER_ID FROM HTML FILE USING "REQUEST.ARGS.GET('')" AND ASSIGN IT INTO A LOCAL VARIABLE
##    (I MAKE USER_ID BE PASSED INTO EVERY HTML FILE (HOMEPAGE,HEALTH CENTER,HEALTH PROVIDER,VIEW BOOKING))
##      EX) USER_ID = REQUEST.ARGS.GET('user_id')
## - 2. GET USER BY PUTTING USER_ID INTO USER_MANAGER.GET_USER FUNCTION.
##      EX) USER = user_manager.get_user(user_id)
## - 3. PASS USER ATTRIBUTES YOU WANT TO USE INTO THAT HTML FILE.
##      EX)render_template("hompage.html",user_id=user.id,user_name=user.name)
#
## 3. HOW TO PASS IN SOME VARIABLES I HAVE IN HTML TO OTHER HTML?
## - TO ACCESS TO USER'S ATTRIBUTES. I THINK IT'S GOOD TO PASS IN USER_ID INTO EVERY HTML FILE SINCE IF WE HAVE USER_ID, THEN WE CAN GET USER
##    EVERYTIME WE WANT TO BY USING user_manager.get_user().
## - FOR DOING THAT, IF YOU HAVE A HYPERLINK IN YOUR HTML AND IT LINKS TO SOME FUNCTION UNDER SOME ROUTE, THEN JUST PASS IN LIKE THIS:
##    IN HTML FILE, <a href ={{url_for('function_name',variable name = variable(u will pass in))}}> Anything </a>
##    YOU CAN ALSO CHECK THAT IN HTML FILE I EDITED.
#
# --------------YOU CAN DELETE THESE COMMENT, ONCE YOU GET THIS --------------------------------
@app.route('/homepage')
@login_required
def homepage():
    user_id = request.args.get('user_id') 
    user = user_manager.get_user(user_id)
    provider = user_manager.get_user("anna@gmail.com")
    centre = centre_manager.get_centre("Sydney Children Hospital")
    # appointment_manager.add_booking("Monday", "2018-10-08", "0930", provider, centre, user, "test")
    if type(user).__name__ == "Patient":
        user_type = "Patient"
    else:
        user_type = "Provider"
    return render_template("homepage.html",user_id=user.id,user_name=user.name,user_type=user_type)
    
@app.route('/health_centre',methods = ['GET', 'POST'])
@login_required
def health_centre():
    user_id = request.args.get('user_id')
    user = user_manager.get_user(user_id)
    if type(user).__name__ == "Patient":
        user_type = "Patient"
    else:
        user_type = "Provider"
    
    if request.method == 'POST':
        centre_name = request.form.get("centre_name")
        suburb = request.form.get("centre_suburb")

        if (centre_name or suburb):
            centres = centre_manager.get_all_centres()
            searchResults = search_centres(centre_name,suburb,centres)
            if searchResults:
                return render_template("health_centre.html",user_id=user.id,user_name=user.name, centres=searchResults,empty=False,user_type=user_type)
            else:
                return render_template("health_centre.html",user_id=user.id,user_name=user.name, centres=searchResults,empty=True,user_type=user_type)
        else:
            return render_template("health_centre.html",user_id=user.id,user_name=user.name, centres = centre_manager.get_all_centres(),empty=False,user_type=user_type)
        
    #If no results, print all centres
    else:
        return render_template("health_centre.html",user_id=user.id,user_name=user.name, centres = sort_now(centre_manager.get_all_centres()),empty=False,user_type=user_type)

#Added for ease:  def _search(centre_name=None,suburb=None,service=None,provider_name=None,system)
@app.route('/health_provider',methods = ['GET', 'POST'])
@login_required
def health_provider():
    user_id = request.args.get('user_id')
    user = user_manager.get_user(user_id)
    if type(user).__name__ == "Patient":
        user_type = "Patient"
    else:
        user_type = "Provider"
        
    if request.method == 'POST':
        suburb = request.form.get("provider_suburb")
        service = request.form.get("provider_service")
        provider_name = request.form.get("provider_name")

        if(suburb or service or provider_name):
            providers = user_manager.get_providers()
            searchResults = search_providers(suburb,service,provider_name,providers)
            print(suburb)
            print(searchResults)
            if searchResults:
                return render_template("health_provider.html",user_id=user.id,user_name=user.name,providers=searchResults,empty=False,user_type=user_type)
            else:
                return render_template("health_provider.html",user_id=user.id,user_name=user.name,providers=searchResults,empty=True,user_type=user_type)
        else:
            return render_template("health_provider.html",user_id=user.id,user_name=user.name,providers=user_manager.get_providers(),empty=False,user_type=user_type)

    else:
        return render_template("health_provider.html",user_id=user.id,user_name=user.name,providers=sort_now(user_manager.get_providers()),empty=False,user_type=user_type)

@app.route('/profile/centre',methods = ['GET', 'POST'])
@login_required
def centre_profile():
    user_id = request.args.get('user_id') 
    user = user_manager.get_user(user_id)
    centre_name = request.args.get('centre_name')
    centre = centre_manager.get_centre(centre_name)
    if type(user).__name__ == "Patient":
        user_type = "Patient"
    else:
        user_type = "Provider"
    for c in centre.providers:
        print(c.rating)
    if request.method == 'POST' and user_type == "Patient":
        rating = int(request.form["rating"])
        if rating > 5 or rating < 0:
            return render_template("profile_centre.html",centre=centre,user=user,user_id=user.id,error=1,user_type=user_type)
        centre.update_rating(rating)
        return render_template("profile_centre.html",centre=centre,user=user,user_id=user.id,error=0,user_type=user_type)
    return render_template("profile_centre.html",centre=centre,user=user,user_id=user.id, error=0, user_type=user_type)

@app.route('/profile/provider',methods = ['GET', 'POST'])
@login_required
def provider_profile():
    user_id = request.args.get('user_id') 
    user = user_manager.get_user(user_id)
    provider_id = request.args.get('provider_id')
    provider = user_manager.get_user(provider_id)
    
    if type(user).__name__ == "Patient":
        user_type = "Patient"
    else:
        user_type = "Provider"
    
    if request.method == 'POST' and user_type == "Patient":
        rating = int(request.form["rating"])
        if rating > 5 or rating < 0:
    	    return render_template("profile_provider.html",provider=provider,user=user,user_id=user.id,error=1,user_type=user_type)
        provider.update_rating(rating)
        return render_template("profile_provider.html",provider=provider,user=user,user_id=user.id,error=0,user_type=user_type)    
        
    return render_template("profile_provider.html",provider=provider,user=user,user_id=user.id,error=0,user_type=user_type)

@app.route('/view_booking',methods = ['GET','POST'])
@login_required
def view_booking():
    user_id = request.args.get('user_id') 
    user = user_manager.get_user(user_id)
    if type(user).__name__ == "Patient":
        bookings = user.get_appointments    
        return render_template("view_booking.html", user_id=user.id, user=user, bookings=bookings)
    else:
        consultations = user.get_consultations
        return render_template("view_consultations.html",user_id=user.id,user_name=user.name,consultations = consultations) 

@app.route('/patient_history',methods = ['GET','POST'])
@login_required
def view_patient_history():
    provider_id = request.args.get('user_id')
    provider = user_manager.get_user(provider_id)
    print("The user id is: {}".format(provider_id))
    time = request.args.get('time')
    date = request.args.get('date')
    # date_current = datetime.datetime.strptime(date, '%Y-%m-%d')
    date_current = datetime.datetime.now().date()
    date_current = datetime.datetime(date_current.year, date_current.month, date_current.day)
    print("The time is: {}".format(time))
    appointments = appointment_manager.date_appointments(date)
    for app in appointments:
        if app.time == time:
            patient = app.patient
            break
    bookings = patient.get_appointments.copy()
    print(bookings)
    i = 0
    while i < len(bookings):
        date_bk = datetime.datetime.strptime(bookings[i].date, '%Y-%m-%d')
        if date_bk >= date_current:
            bookings.remove(bookings[i])
            i = i - 1
        i = i + 1
    return render_template("patient_history.html", bookings=bookings, patient=patient, provider_id=provider_id, provider=provider)

@app.route('/edit_notes', methods=['GET','POST'])
@login_required
def edit_notes():
    user_id = request.args.get('user_id')
    admin = request.args.get('admin')
    print(admin)
    date = request.args.get('date')
    time = request.args.get('time')
    # consultation = request.args.get('consultation')
    consultation = appointment_manager.get_consultation(date, time)
    # print("Consultation is : {} {}".format(consultation.patient.name, consultation.centre.name))
    print("The date and time is: {} {}".format(date, time))
    consultations = request.args.get('consultations')
    if request.method == 'POST':
        user_id = request.args.get('user_id')
        user = user_manager.get_user(user_id)
        # consultations = request.args.get('consultations')
        notes = request.form['notes']
        print(notes)
        date = request.args.get('date')
        time = request.args.get('time')
        consultation = appointment_manager.get_consultation(date, time)
        print("The name of patient is: {}".format(consultation.patient.name))
        consultation.notes = ""
        consultation.notes = notes
        consultations = user.get_consultations
        return render_template("view_consultations.html",user_id=user.id,user_name=user.name,consultations = consultations)

    return render_template("consultation_notes.html", user_id=user_id, consultation=consultation, consultations=consultations, admin=admin)

@app.route('/select_provider', methods=['GET','POST'])
@login_required
def select_provider():
    user_id = request.args.get('user_id')
    centre_name = request.args.get('centre_name')
    centre = centre_manager.get_centre(centre_name)
    if request.method == 'POST':
        provider_id = request.form['provider']
        #print("The name of the provider selected is: {}".format())
        return redirect(url_for('select_date', provider_id=provider_id, user_id=user_id, centre_name=centre_name))
    return render_template("select_provider.html", centre=centre, user_id=user_id)

@app.route('/select_centre', methods=['GET','POST'])
@login_required
def select_centre():
    user_id = request.args.get('user_id')
    print("The id is: {}".format(user_id))
    user = user_manager.get_user(user_id)
    provider_id = request.args.get('provider_id')
    provider = user_manager.get_user(provider_id)
    if request.method == 'POST':
        centre_name = request.form['centre']
        print("This is the name originally: {}".format(centre_name))
        return redirect(url_for('select_date', provider_id=provider_id, user_id=user_id, centre_name=centre_name))
    return render_template("select_centre.html", provider=provider, user=user, user_id=user_id)


#@app.route('/select/date', methods=['GET', 'POST'])
@app.route('/booking_date', methods=['GET', 'POST'])
@login_required
def select_date():
    user_id = request.args.get('user_id')
    provider_id = request.args.get('provider_id')
    centre_name = request.args.get('centre_name')
    min_date = datetime.date.today()
    if request.method == 'POST':
        date = request.form['date']
        days = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']
        dt = datetime.datetime.strptime(date, '%Y-%m-%d')
        day = days[dt.weekday()]
        return redirect(url_for('select_time', provider_id=provider_id, user_id=user_id, centre_name=centre_name, date=date, day=day))
 
    return render_template('select_date.html', min_date=min_date, user_id=user_id)
    
@app.route('/booking_time', methods=['GET', 'POST'])
@login_required
def select_time(): 
    provider_id = request.args.get('provider_id')
    user_id = request.args.get('user_id')
    centre_name = request.args.get('centre_name')
    date = request.args.get('date')
    day = request.args.get('day')
    
    format = datetime.datetime.strptime('9:00 AM', '%I:%M %p')
    times = []
    times.append(format.strftime('%I:%M %p'))
    for i in range(30,60*12,30):
        times.append((format+datetime.timedelta(minutes=i)).strftime('%I:%M %p'))
    '''
    appointments = []
    appointments = appointment_manager.date_appointments(date)
    print("The name of centre is: {}".format(centre_name))
    '''
    user = user_manager.get_user(user_id)
    provider = user_manager.get_user(provider_id)
    centre = centre_manager.get_centre(centre_name)
    
    unavailable_times = appointment_manager.get_times(date, provider)
    for t in unavailable_times:
        if t in times:
            times.remove(t)
    
    if request.method == 'POST':
        time = request.form["time"]
        reason = request.form["reason"]
        #return render_template('confirm_booking.html', time=time)
        
        appointment_manager.add_booking(day, date, time, provider, centre, user, reason)
        #app = appointment_manager.get_appointments(user)
        #user.add_appointments(app)
        return redirect(url_for('confirm_booking',user_id=user_id))
    
    return render_template('select_time.html', times=times,provider=provider, centre=centre, user_id=user_id)

@app.route('/booking_confirm', methods=['GET'])    
@login_required
def confirm_booking():
    user_id = request.args.get('user_id')
    return render_template('confirm_booking.html',user_id=user_id)
