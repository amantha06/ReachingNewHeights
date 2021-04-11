#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 10 13:51:47 2021

@author: amdocs
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import joblib

from sklearn.ensemble import RandomForestRegressor

train = pd.read_csv('https://raw.githubusercontent.com/amantha06/ReachingNewHeights/main/HeightData.csv')
# test = pd.read_csv('https://raw.githubusercontent.com/amantha06/ReachingNewHeights/main/HeightData.csv')

train['Gender'] = train['Gender'].map({'M': int(1), 'F': int(0)})
# test['Gender'] = test['Gender'].map({'M': int(1), 'F': int(0)})

X = train.drop(['Height'], axis=1)
y = train['Height']
# X_test  = test.drop(['Height'], axis=1).copy()


mdl = RandomForestRegressor(n_estimators=100)
mdl.fit(X, y)
'''
random_forest_Y_pred = random_forest.predict(X_test).round(decimals=1)
random_forest.score(X_train, Y_train)
random_forest_accuracy = random_forest.score(X_train, Y_train)
print(random_forest_accuracy)

random_forest_submission = pd.DataFrame({"Height": random_forest_Y_pred})
random_forest_submission.to_csv('random_forest_submission.csv', index=False)
'''

import joblib

joblib.dump(mdl, 'model.pkl')


mdl = joblib.load('model.pkl')
model_columns = list(X.columns)
joblib.dump(model_columns, 'model_columns.pkl')
