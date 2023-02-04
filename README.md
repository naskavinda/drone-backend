### Assumptions
- Newly Registered drone state will be `IDEL`.
- Drone can't deliver medications to the multiple location in a one single tripp.
- Medications can be loaded in to the drone until it's in `IDLE` OR `LOADING` states.
- Medication image saved in the Aws s3(or Azure blob storage) and assume that imageUrl saved in the Database.
- 