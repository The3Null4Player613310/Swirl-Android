# CS2130GroupProject

### Project Title:
Swirl

### Project Description:

An educational tool allowing students to examine and compare the outputs of a simplified stochastic population model. The model will track populations by age/sex and have age/sex related mortality and reproduction. The model will also take in a carrying capacity for the population, yearly harvest and supplement values, and the simulated variation in all values.

### Use Cases:

1. A student wishes to see the results of a certain simple stochastic population model, using the provided parameters.

2. Student wishes to open preset parameters provided by professor.

3. A student wishes to compare the results of two different parameter sets.

4. A student wishes to compare stochastic modelling with simple non-stochastic models.

5. A student wishes to export the results in a common format, such as .csv

### Dependencies

1. Dependent on the GraphView Library or similar

2. Currently to goal is to have the simulation engine be light enough to run locally, but this may require an external server

3. Currently we are considering several optional components that would require interfacing with external resources, such as an external server running Vortex (current research standard for stochastic population modelling) or hosting configuration files.

### Potential Contributions of Members

@allanstewart is planning to take point on the simulation engine

@nathanmunn is planning to take point on the UI elements

The project report will be a unified effort as will the in class presentation and demonstration.

### Potential hurdles

We are not yet certain which graph utilities we will be using or how.

We are not yet sure how computationally intense the simulation engine will be. We plan to design the engine to be as modular and independent of implementation as possible to allow it to be implemented in an external server if necessary, and to allow it to be reused, should it be desirable later on.

We are concerned about the stability of the engine should the app be terminated by the user.

We have a number of options we are considering beyond the core model being considered, and they are as of yet of undetermined complexity.

We are as of yet unsure how we will be able to have a professor share configuration files through the UPEI network, which is paranoid.

### Target Audience / Existing Similar Apps

The app would be used primarily by students studying population modelling, but may also be useful to researchers looking to try toy models, prior to full simulation.

### Testing

The engine performance will have to be tested extensively, and its stability under a number of situations, such as rotation, erroneous input, app closure, must be examined.

The validity of the results of the engine will have to be tested, most likely by comparing the output to research grade simulation output.

### Project Timeline:

Milestone 1: Determine the interface between the GUI, engine, and graphing library: Estimated Oct 25

Milestone 2: Have core model implemented so as to evaluate viability of local deployment: Estimated Nov 2

Milestone 3: Have the basic functionality for use case 1: Estimated Nov 7

Milestone 4: Have the basic functionality for use case 5: Estimated Nov 15

Milestone 5: Testing and debugging. Estimated Nov 20

Time permitting we will pursue a number of simple extensions to the model and functionality. Estimated November 25.

Milestone 6: Completed project report Nov 28

Milestone 7: Completed Application Nov 28

Milestone 8: In-class presentation. Nov 28-30?
