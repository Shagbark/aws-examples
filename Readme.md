#### SNS

Here will be a configuration for working with SNS locally using LocalStack

##### Topic

arn:aws:sns:eu-central-1:000000000000:cache-invalidation

##### SNS message to subscribers 

```json
{
    "Type": "Notification",
    "MessageId": "f82fba51-b1aa-4e2a-b640-7291df5a598e",
    "TopicArn": "arn:aws:sns:eu-central-1:000000000000:cache-invalidation",
    "Message": "Hello AWS SNS!",
    "Timestamp": "2021-01-23T22:21:31.094Z",
    "SignatureVersion": "1",
    "Signature": "EXAMPLEpH+..",
    "SigningCertURL": "https://sns.us-east-1.amazonaws.com/SimpleNotificationService-0000000000000000000000.pem"
}
```