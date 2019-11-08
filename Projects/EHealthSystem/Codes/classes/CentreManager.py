from classes.Centre import Centre

class CentreManager:
    def __init__(self):
        self._centres = []

    def add_centre(self,name,suburb,phone,rating,nratings,providers):
        new_centre = Centre(name,suburb,phone,rating,nratings,providers)
        for centre in self._centres:
            if centre.name == new_centre.name:
                return False
        self._centres.append(new_centre)
        return True

    # Assume that every centre has a different name
    def get_centre(self,name):
        for centre in self._centres:
            if centre.name == name:
                return centre
        return None

    def get_all_centres(self):
        return self._centres
