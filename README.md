# Flower Delivery App
This is a very basic MVP of what a flower delivery app would look like. The app has 2 screens:
1. A list of all available orders. These orders are pulled from a mock API (using mockable.io) or if the phone has no internet connection, from an offline database which is updated regularly.
2. A details page where you can see more information on the order with the possibility of changing the status of the order. Currently this status is only saved locally in the offline database.

#Solution description
- **Activity and Fragments** for screens.
- **Databinding** to for a cleaner code in the Activity and Fragment.
- **MVVM** for screen architecture.
- **Retrofit** for the communication with the online mock database.
- **Room** in order to save the said data offline, when internet connectivity is not available.
