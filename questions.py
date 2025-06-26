import csv
import json
import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

with open('questions.csv', newline='', encoding='utf-8') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        question_data = {
            'id': row['id'],
            'question': row['question'],
            'options': json.loads(row['options']),
            'correctAnswer': int(row['correctAnswer'])
        }
        db.collection('quiz_questions').document(row['id']).set(question_data)

print("Upload complete.")
