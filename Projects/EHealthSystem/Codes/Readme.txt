Writer : Younggil Tak (Tom)
Date: Tak 14/9/2018
Updated: Sam 24/09/2018
Updated2: Tom 06/10/2018
Updated: Emma 14/10/2018

< How to use >

1. Install Required modules

 This file doesn't have venv file (since it takes up too much space). So, you need to install it. Follow this command.

 - 1. virtualenv --python=python3 venv
 - 2. source venv/bin/activate
 - 3. pip3 install -r module_list.txt
   (module_list.txt has a list of module you need to run this program)
   
2. Run main file. 
 
 Once you've installed all the required moudules. Run the main file.
 
 - 1. python3 run.py 
 
< How to use Database System>

1. All the data is stored in csv/pkl files.
2. Every time this program runs, program memory data will be initialized based on the data stored in pkl files.
3. Since data in pkl files are not human readable, you can check stored data from csv files in DB folder.
4. If you want to change the database back into the initial one, just run DB_initializer.py

< How to run tests >

With the virtual environment activated, run:

 - 1. pip3 install pytest
 - 2. pytest -v tests.py

