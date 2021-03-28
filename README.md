# Challenge

## Data Persistence feature
  - Single load of list   
      -The API is loaded when the cache data is empty, it is then saved to local database. Also added a Refresh function on the toolbar to fetch a fresh data and remove cached data such a last visited date time and visit count. This feature is added to save data used on loading API.
  - Latest date and time when the item is visited   
  		- Saves the latest date time the item was visited, the date and time can be viewed from the Track List and Track details. This feature was added to be able to track the visit date and time.
  - Saves the count of visit times   
  		- Saves the visit times , the count time can be seen from the Track Details.  This feature was added to be able to track the visit times.
  - Save the last screen where the user was on  
  		- Save the last screen visible from the user, then upon opening the app it can check the whether it is on the master list or details page. This feature was added to help user return to specific activity when app is accidentally close

## MVVM Design Pattern 
Model-View-ViewModel is used to lessen boilerplace (vs. othe design pattern such as MVP). The viewmodel is a good tool to save and manage data after configuration changes and no more manual lifecycle handling. MVVM can also be used alongside with databinding wherein it can automatically update the view based on the data provided. But the disadvantage comes from it's advantage, the databinding can cause unrelated errors which can hard to debug, business logic can be added on the xml which can be make a project more complex,  Databinding can also increase the app size.   
   A repository layer was also added to manage the origin of the data and can provide a single source of data.  
