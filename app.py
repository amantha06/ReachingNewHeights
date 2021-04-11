#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 10 20:00:10 2021

@author: amdocs
"""
from flask import Flask, request, jsonify
import joblib
import traceback
import pandas as pd
import numpy as np

app = Flask(__name__)
@app.route('/predict', methods=['POST'])
def predict():
    if mdl:
        try:
            json_ = request.json
            print(json_)
            query = pd.get_dummies(pd.DataFrame(json_))
            query = query.reindex(columns=model_columns, fill_value=0)

            prediction = list(mdl.predict(query))

            return jsonify({'prediction': str(prediction)})

        except:
            return jsonify({'trace': traceback.format_exc()})
    else:
        return ('No model here to use')

if __name__ == '__main__':
    try:
        port = int(sys.argv[1])
    except:
        port = 12345 
    mdl = joblib.load("model.pkl")
   
    model_columns = joblib.load("model_columns.pkl")
  
    app.run(port=port, debug=True)