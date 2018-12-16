# SpotifyAlbumSearch
### To run:
- If you don't have access to SECRET AND CLIENT_ID follow this guide https://developer.spotify.com/documentation/general/guides/authorization-guide/#client-credentials-flow
- Paste SECRET AND CLIENT_ID to ...ServiceGenerator.DEFINITELY_NOT_SECRET and ...ServiceGenerator.ABSOLUTELY_NOT_CLIENT

### has
- remoteRx module (java library) for remote calls
- app module (android app module) for ui and logic
- common (java library without external dependencies) for entities

### does
- authenticatate client
- load albums from query
- present retrieved albums
- load album details
- present retrieved details
- play preview of first album song
 
### uses
- architecture components (viewModel, liveData, databinding)
- Rx-java, Rx-android, moshi converter, rxjava adapter
- material components, contstraint layout
