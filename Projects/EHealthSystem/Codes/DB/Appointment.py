class Appointment():
    def __init__(self, day, date, time, provider, centre, patient, reason):
        self._day = day
        self._date = date
        self._time = time
        self._provider = provider
        self._centre = centre
        self._patient = patient
        self._reason = reason

    @property
    def day(self):
        return self._day

    @property
    def date(self):
        return self._date
        
    @property
    def time(self):
        return self._time
        
    @property
    def provider(self):
        return self._provider
        
    @property
    def centre(self):
        return self._centre
        
    @property
    def patient(self):
        return self._patient
	
    @property
    def reason(self):
        return self._reason	
	
    def __str__(self):
        return "Day: {}, Date: {}, Time: {}, Reason for visit: {}".format(self._day, self._date, self._time, self._reason)