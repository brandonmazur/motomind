# hack:now 
This is our entry for the 2020 hack:now hackathon. We decided to focus on productivity for people who need some extra motivation to keep up with their work during the quarantine. Our hack included a website and a twilio SMS backend. For all of us this was our first hackathon and large side project, so this was a huge learning process and we are with the progress we've made. A little details on the implementation:

- We utilized twilio's API in order to communicate with a users phone and our backend server. We chose SMS because of the accessibility.
- Incorporated the Spark library in order for the backend to be able to communicate with the twilio API and our webserver
- Created a (currently unsecure, but working) database which allows for reminders and information to be stored statically for future use instead of only being instance data. We decided to try to incorporate our own database instead of an online resource since we figured it would be a good learning experience (which it ended up being).
