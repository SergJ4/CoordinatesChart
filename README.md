# CoordinatesChart

The project was created to perform a test task:

Mobile app must fetch (x, y) points bunch from server api and then display it on screen using table and line chart. 
Main screen has info text, input field for required points count and button to start http POST request to https://demo.bankplus.ru/mobws/json/pointsList. "version=1.1" and "count" params must be passed in. There are some server errors that app must be able to handle correctly (errors are generated randomly on server side). If error with code "-1" received, then it contains "message" field with Base64 encoded error string. It must be decoded and displayed on screen. Problem with ssl certificate is simulated on server side. This one should be bypassed in order to fetch any data from api.
If data successfully received then it should be passed to next screen. Second screen should display fetched coordinates in table and line chart below. Points on chart must be in ascending order of the x coordinate.

Additional tasks:
- User can zoom in/out chart (not implemented)
+ Connect points using smooth lines (implemented)
+ Correct portrait and landscape orientation handling (implemented without additional layout xml files)
+ Save chart image to file (implemented)


Technologies stack:
Kotlin, MVVM, Google Architecture components ViewModel, LiveData, Dagger 2 (Android Injector), Retrofit
