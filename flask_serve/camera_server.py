from flask import Flask
from flask import request
import sys
import os


app = Flask(__name__)

@app.route("/signal")
def signal():
    camera = request.args.get("camera")
    shots = request.args.get("shots")

    sysparams = "python3 camera_util.py "+camera+" "+str(shots)
    os.system(sysparams)

    return "proximity sensor signal received "+sysparams

if __name__ =="__main__": app.run(debug = False)
