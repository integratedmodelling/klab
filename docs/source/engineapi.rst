k.LAB modeling engine HTTP API 
==============================

.. http:get:: /users/(int:user_id)/posts/(tag)

   The posts tagged with `tag` that the user (`user_id`) wrote.

   :query sort: one of ``hit``, ``created-at``
   :query offset: offset number. default is 0
   :query limit: limit number. default is 30
   :reqheader Accept: the response content type depends on
                      :mailheader:`Accept` header
   :reqheader Authorization: mandatory task token
   :resheader Content-Type: this depends on :mailheader:`Accept`
                            header of request
   :statuscode 200: no error
   :statuscode 404: there's no user


**Example request**:

..  http:example:: curl wget

    GET /users/123/posts/web.json HTTP/1.1
    Host: example.com
    Accept: application/json
    Authorization: Basic YWRtaW46YWRtaW4=


**Example response**:

.. sourcecode:: http

      HTTP/1.1 200 OK
      Vary: Accept
      Content-Type:application/json

      [
        {
          "post_id": 12345,
          "author_id": 123,
          "tags": ["server", "web"],
          "subject": "I tried Nginx"
        },
        {
          "post_id": 12346,
          "author_id": 123,
          "tags": ["html5", "standards", "web"],
          "subject": "We go to HTML 5"
        }
      ]

