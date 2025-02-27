from flask import Flask, request, jsonify

app = Flask(__name__)

# 예제 데이터: 간단한 메모리 기반 데이터 저장소
items = {
    1: {"name": "Item 1", "description": "첫 번째 아이템 설명입니다."},
    2: {"name": "Item 2", "description": "두 번째 아이템 설명입니다."}
}

# 루트 엔드포인트: 간단한 환영 메시지를 반환합니다.
@app.route('/', methods=['GET'])
def index():
    return "Flask API에 오신 것을 환영합니다!"

# GET 요청: 전체 아이템 목록 반환
@app.route('/items', methods=['GET'])
def get_items():
    return jsonify(items)

# GET 요청: 특정 아이템의 상세 정보를 반환 (URL 파라미터 사용)
#@app.route('/items/<int:item_id>', methods=['GET', 'POST'])
@app.route('/items/<int:item_id>', methods=['GET'])
def get_item(item_id):
    item = items.get(item_id)
    if item:
        return jsonify(item)
    else:
        return jsonify({"error": "아이템을 찾을 수 없습니다."}), 404

# POST 요청: 새로운 아이템 추가
@app.route('/items', methods=['POST'])
def create_item():
    # 클라이언트에서 전송한 JSON 데이터를 파싱합니다.
    new_data = request.get_json()
    # 필수 필드 확인
    if not new_data or 'name' not in new_data or 'description' not in new_data:
        return jsonify({"error": "유효하지 않은 데이터입니다."}), 400

    new_id = max(items.keys()) + 1 if items else 1
    items[new_id] = {"name": new_data["name"], "description": new_data["description"]}
    return jsonify({"message": "아이템이 생성되었습니다.", "id": new_id}), 201

# PUT 요청: 기존 아이템 업데이트
@app.route('/items/<int:item_id>', methods=['PUT'])
def update_item(item_id):
    update_data = request.get_json()
    if not update_data:
        return jsonify({"error": "전송된 데이터가 없습니다."}), 400

    item = items.get(item_id)
    if not item:
        return jsonify({"error": "아이템을 찾을 수 없습니다."}), 404

    # 전달된 데이터로 아이템 업데이트 (존재하는 필드만 수정)
    item.update(update_data)
    return jsonify({"message": "아이템이 업데이트되었습니다.", "item": item})

# DELETE 요청: 아이템 삭제
@app.route('/items/<int:item_id>', methods=['DELETE'])
def delete_item(item_id):
    if item_id in items:
        del items[item_id]
        return jsonify({"message": "아이템이 삭제되었습니다."})
    else:
        return jsonify({"error": "아이템을 찾을 수 없습니다."}), 404

if __name__ == '__main__':
    app.run(debug=True, port=5000)