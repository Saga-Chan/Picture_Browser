from rest_framework import serializers

from server.models import ResponseServer

from server.models import ImageSearch




class ImageSearchSerializer(serializers.ModelSerializer):
    class Meta:
        model = ImageSearch
        fields = ('id', 'date', 'client', 'response', 'score')



