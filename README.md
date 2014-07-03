## Glass Wizard Demo ##
Victor Kaiser-Pendergrast

### About ###
A simple demo illustrating how to put together a wizard-like interface on Glass in a single Activity. By defining multiple menus and switching them out (depending on the current step) with `invalidateOptionsMenu()`, multiple different questions can be made. A single `enum` contains the state that the Wizard is currently in.

This demo has four states:
- Start: a short "tap to get started" message
- Option1: asks for a color - either red or blue
- Option2: asks for a number between 1 and 5
- End: shows the selected number in the color picked, along with a "tap to finish" message

### Screenshots ###
