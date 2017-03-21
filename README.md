# pipes-and-filters

It is my implementation of 'Pipes and Filters' messaging pattern => http://www.enterpriseintegrationpatterns.com/patterns/messaging/PipesAndFilters.html
To run provided test simply execute `sbt test`

I've made assumption that authentitaction phase is long lasting - call to external API or DB. 

Implementation is based on akka-streams which provides an easy way to to make your flow parallelized, asynchronous and back-pressured.
