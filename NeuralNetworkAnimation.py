from manim import *
 
class NeuralNetworkAnimation(Scene):
    def construct(self):
        inputNeuronList = []
        numberOfInputNeurons = 5
        for i in range(numberOfInputNeurons):
            inputNeuronList.append(Circle(radius=.2,color=BLUE))
        for i in range(len(inputNeuronList)):
            inputNeuronList[i].shift(DOWN * i)
            self.play(Create(inputNeuronList[i]))
# you can edit code in github repositorys though github directly         


            
