#リポジトリ名
datahub-rcv-carrier

#リポジトリの概要
共同輸送システムにデータを送受信するためのAPI
	
#機能説明	
登録（更新）：HTTPレスポンスにてDBから抽出したZip圧縮されたCSVデータを、JSONデータに変換し、HTTPリクエストにて共同輸送システムに送信する
削除：削除したいレコードの主キーとなる値をクエリパラメータにしHTTPリクエストにて共同輸送システムに送信する
参照：HTTPレスポンスにて共同輸送システムから受信したJSONデータをCSVデータに変換、Zip圧縮し、HTTPリクエストにてDBに格納する

#使用言語
JavaSE-17

#環境
Gradle 8.10.2
Eclipse 2023-12 (4.30.0)

#システム構成図、開発環境の構築方法
●dahub-rcv-carrier
　─src
    └─main
        ├─java
        │  └─datahub
        │      │  DatahubCsctojsonApplication.java
        │      │
        │      ├─constants
        │      │      Constants.java
        │      │
        │      ├─entity
        │      │      Arguments.java
        │      │      ErrorResponse.java
        │      │      FileCreateRequest.java
        │      │      FileCreateResponse.java
        │      │      FileDownloadRequest.java
        │      │      FileDownloadResponse.java
        │      │      FileStatusRequest.java
        │      │      FileStatusResponse.java
        │      │      LoginInfo.java
        │      │      ShipperTransCapacity.java
        │      │      VehicleMaster.java
        │      │
        │      ├─exception
        │      │      DataHubException.java
        │      │
        │      ├─job
        │      │      rcvJob.java
        │      │
        │      ├─jsonData
        │      │      JsonDataToAPI.java
        │      │      TransportCapacity.java
        │      │      TransportPlan.java
        │      │      Vehicle.java
        │      │
        │      ├─Service
        │      │      ConvertCsvToJsonPattern3012.java
        │      │      ConvertCsvToJsonPattern4902.java
        │      │      ConvertCsvToJsonPattern5001.java
        │      │
        │      └─step
        │              AddHeader.java
        │              BaseStep.java
        │              Check.java
        │              CreateEventIdStep.java
        │              CreateJsonStep.java
        │              CsvReadStep.java
        │              DeleteFileStep.java
        │              ExternalApiConnectionsStep.java
        │              ExternalApiLoginStep.java
        │              RequestFileCreateStep.java
        │              RequestFileDownloadStep.java
        │              RequestFileStatusStep.java
        │              UnzipFileStep.java
        │
        └─resources
                application.properties
                logback-spring.xml


#使い方
●dahub-rcv-carrier
	--変数について--
		application.propertiesの
		api_url_files_create
		にファイル作成URLを入力
		api_url_files_status
		にステータス確認用URLを入力
		api_url_files_download	
		にファイルダウンロード要求用のURLを入力する

		30121_REGISTER	
		30122_REGISTER
		4902_REGISTER		
		5001_REGISTER
		に外部接続用URLを入力する
		
		30121_DELETE
		30122_DELETE
		4902_DELET
		5001_DELETE
		に外部接続削除用URLを入力する

		LOGIN
		APIKEY
		ID
		SECRET
		に外部認証API接続先のURL、APIKEY、ID、SECRETを入力する

	--CsvToJson変換について--
	■項目に関して
		JSONのオブジェクト=Javaのモデルクラス
		CSVヘッダの情報を参照して、
		各レコードの値からエンティティクラスを生成していく

	■繰り返しに関して
		ModelをListとして保持する

#問合せ及び要望に関して
本リポジトリは現状は主に配布目的の運用となるため、IssueやPull Requestに関しては受け付けておりません

#ライセンス
このプロジェクトは MITライセンス のもとで公開されています
特筆が無い限り、ソースコードおよび関連ドキュメントの著作権はヤマト運輸株式会社に帰属します

#免責事項
本リポジトリの内容は予告なく変更・削除する可能性があります
本リポジトリの利用により生じた損失及び損害等について、いかなる責任も負わないものとします
	
