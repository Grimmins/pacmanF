import PySimpleGUI as sg

lines = columns = 6
main_layout = []

#Set size
sz = (2,2)

#North = 0
#South = 1
#East = 2
#West = 3

#TODO  les wall commutent en tore,le système de traduction vers une string, factoriser les trucs moches, Mettre les boutons vide en blanc ou autre, Système de d'arguments ligne de commande

#Top Screen menu definition
menu_def = [
   ['Cells', ['Pacgum', 'Energizer', 'Pacman', 'Blinky', 'Pinky','Inky','Clyde']]
]

#Permet de savoir quel action est liée au click
#0 = Pacgum
#1 = Energizer
#2 = Pacman
#3 = Blinky
#4 = Pinky
#5 = Inky
#6 = Clyde
setterCell = 0

#Définit les symboles
symbols = [".", "E", "pac","blk","pik","ink","cly"]

#Gère si pacman ou un fantome a déjà été placé
placed = {"pac":False, "blk":False, "pik":False, "ink":False, "cly":False}

#Rajoute le menu
main_layout.append([sg.Menu(menu_def)])

#Initalizing grid layout
for i in range(lines):#lines
    layout_row = []
    #Generates north walls
    for j in range(columns):
        if i == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == columns - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        else:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))

    main_layout.append(layout_row)


    layout_row = []
    #Generates east and west walls
    for j in range(columns):
        if j == 0:
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
        elif j == columns - 1:
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
        else:
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
    main_layout.append(layout_row)


    layout_row = []
    #Generates south walls
    for j in range(columns):
        if i == lines - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == columns - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        
        else:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))

    main_layout.append(layout_row)



#Generates wallsStates for walls : False = Empty, True = Full
wallsStates = {(i,j,orientation) : False for i in range(lines) for j in range(columns) for orientation in range(4)}
for i in range(lines):
    for j in range(columns):
        for k in range(4):
            if i == 0:
                wallsStates[i,j,0] = True
            if j == 0:
                wallsStates[i,j,3] = True
            if i == lines - 1:
                wallsStates[i,j,1] = True
            if j == columns - 1:
                wallsStates[i,j,2] = True


cellsStates = {(i,j) : -1 for i in range(lines) for j in range(columns)}


#Initializes the window
window = sg.Window("Test", main_layout,background_color="black")

#Event Loop
while True:

    #Needed for event gestion
    event, values = window.read()

    #Finit le programme si la fenêtre est fermée
    if event == sg.WIN_CLOSED:
        break

    #Permet de gérer les events du menu permettant de set les cellules
    if event == "Pacgum":
        setterCell = 0
    if event == "Energizer":
        setterCell = 1
    if event == "Pacman":
        setterCell = 2
    if event == "Blinky":
        setterCell = 3
    if event == "Pinky":
        setterCell = 4
    if event == "Inky":
        setterCell = 5
    if event == "Clyde":
        setterCell = 6

    
    #Permet de gérer le changement des cellules

    if event.startswith("-CELL"):

        #Récupère les coordonnées de la cellule cliquée
        x,y = int(event[6]),int(event[9])


        if setterCell >= 2:
            if not placed[symbols[setterCell]]:
                cellsStates[x,y] = (x,y)
                window[f"-CELL{x,y}-"].update(symbols[setterCell])
                placed[symbols[setterCell]] = (x,y)
            
            else:
                window[f"-CELL{placed[symbols[setterCell]]}-"].update("Cell")
                cellsStates[x,y] = (x,y)
                placed[symbols[setterCell]] = (x,y)
                window[f"-CELL{x,y}-"].update(symbols[setterCell])


        else:
            #Affiche le changement à l'écran
            window[f"-CELL{x,y}-"].update(symbols[setterCell])

            #Met à jour le dictionnaire
            cellsStates[x,y] = setterCell


    #Permet de détecter les walls
    if event.startswith("-WALL"):

        #Récupère les coordonnées du wall cliqué 
        x,y,o = int(event[6]), int(event[9]), int(event[12])

        value = wallsStates[x,y,o]
        wallsStates[x,y,o] = not value
        window[f"-WALL{x,y,o}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))

        if not ((x == 0 and o == 0) or (x == lines - 1 and o == 1)):
            if o == 0:
                window[f"-WALL{x-1,y,1}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                wallsStates[x-1,y,1] = wallsStates[x,y,o]
            if o == 1:
                window[f"-WALL{x+1,y,0}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                wallsStates[x+1,y,0] = wallsStates[x,y,o]

        
        if not ((y == 0 and o == 3) or (y == columns - 1 and o == 2)):
            if o == 3:
                window[f"-WALL{x,y-1,2}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                wallsStates[x,y-1,2] = wallsStates[x,y,o]
            if o == 2:
                window[f"-WALL{x,y+1,3}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                wallsStates[x,y+1,3] = wallsStates[x,y,o]

        
        if (x == 0 and o == 0):
            window[f"-WALL{columns-1,y,1}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
            wallsStates[columns-1,y,1] = wallsStates[x,y,o]

        print(x,y,o)


window.close()