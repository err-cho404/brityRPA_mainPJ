from datetime import datetime
import requests
import uuid
import time
import json
import glob
import pandas as pd
import re

monthinput=input('읽어들일 영수증 사용 월을 입력하십시오 (예 : 08) 입력값 : ')
nowyear = str(datetime.now().year)
path = "./receipt/"+ nowyear+monthinput +"/*"
file_list = glob.glob(path)
file_list_png = [file for file in file_list if file.endswith(".png")]
receipt_list=[]

# CLOVA OCR 호출
for i in file_list_png :
  api_url = 'api_url'
  secret_key = 'api_key'
  image_file = i  
  output_file = './output.json'

  request_json = {
      'images': [
          {
              'format': 'png',
              'name': 'demo'
          }
      ],
      'requestId': str(uuid.uuid4()),
      'version': 'V2',
      'timestamp': int(round(time.time() * 1000))
  }

  payload = {'message': json.dumps(request_json).encode('UTF-8')}
  files = [
    ('file', open(image_file,'rb'))
  ]
  headers = {
    'X-OCR-SECRET': secret_key
  }

  response = requests.request("POST", api_url, headers=headers, data = payload, files = files)

  res = json.loads(response.text.encode('utf8'))
  receipt_history=[]
  
  try:
    with open(output_file, 'w', encoding='utf-8') as outfile:
        json.dump(res, outfile, indent=4, ensure_ascii=False)
        
        json_test=res['images'][0]["receipt"]['result']
        if ('진동벨' in json_test['storeInfo']['name']['text'])!=True :
          receipt_history.append(json_test['storeInfo']['name']['text'])
        else :
          receipt_history.append('error')
        if (json_test['storeInfo']['bizNum']['text'].replace('-','')).isdigit()==True:
          receipt_history.append(json_test['storeInfo']['bizNum']['text'].replace('-',''))
        else :
          receipt_history.append('error')
        receipt_history.append(json_test['storeInfo']['addresses'][0]['text']) 
        receipt_history.append('20'+re.sub(r'[^0-9]', '', json_test['paymentInfo']['date']['text'])[-6:])
        receipt_history.append(json_test['totalPrice']['price']['text'].replace('.','').replace(',','')) 
        receipt_list.append(receipt_history)
  except :
        receipt_list.append(['error','','','',''])
        print(image_file+'해당 영수증은 읽을 수 없습니다.')

df=pd.DataFrame(receipt_list,columns = ['storename', 'bizNum', 'addresses','date','price'])
df.to_excel('./'+nowyear+monthinput+'receipts.xlsx')