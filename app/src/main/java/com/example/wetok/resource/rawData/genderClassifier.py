import nltk
from nltk.corpus import names
import random
"""
This file use nlp algorithm to classify username's gender
@author Xinyue Hu
"""
def gender_features(word):
    return {'last_letter': word[-1]}

labeled_names = ([(name, 'male') for name in names.words('male.txt')] +
[(name, 'female') for name in names.words('female.txt')])

random.shuffle(labeled_names)

featuresets = [(gender_features(n), gender) for (n, gender) in labeled_names]
train_set, test_set = featuresets[500:], featuresets[:500]
classifier = nltk.NaiveBayesClassifier.train(train_set)

list = []
with open('firstName.txt','r') as f:
    line = f.readline()
    while line != '':
          var = line.split('\n')[0]
          list.append(var)
          line = f.readline()
    f.close()

with open('gender.txt', 'w') as f:
    for i in range(len(list)):
        str1 = classifier.classify(gender_features(list[i]))
        f.write(str1)
        f.write('\n')
    f.close()