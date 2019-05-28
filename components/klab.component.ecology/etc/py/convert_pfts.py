import re
import sys

with open(sys.argv[1]) as f:
	lines = f.readlines()

group_names = []
group_datas = []
group_bases = []

for line in lines:
	print "# %s" % line
	loc_of_comment = line.find('!')
	if loc_of_comment != -1:
		line = line[:loc_of_comment-1]

	if "group" in line or "pft" in line:
		group_name = re.search('"(\w+)"', line).group(1)
		group_data = {}
		bases = []
		print "New group: %s" % group_name

	elif line.startswith("\t!"):
		continue

	elif line.startswith("\t"):
		spl = line.split()
		if len(spl) == 1:
			print "Reference to another base: %s" % spl[0]
			bases.append(spl[0])
		elif len(spl) >= 2:
			key, value = spl[0].strip(), spl[1].strip()


			if key == "lifeform":
				value = "LifeForm." + value.upper().replace('"', '')
			elif key == "phenology":
				value = "Phenology." + value.upper().replace('"', '')
			elif key == "pathway":
				value = "PhotosynthesisPathway." + value.upper().replace('"', '')
			elif key in ['rootdist', 'fertdates', 'fert_stages', 'fertrate', 'photo']:
				value = "new double[]{" + ",".join(spl[1:]) + "}"
			elif key in ["include"]:
				continue

			group_data[key] = value
			print "Data %s = %s" % (key, value)

	elif ")" in line:
		group_names.append(group_name)
		group_datas.append(group_data)
		group_bases.append(bases)



print "Output below:"
print 
print
print 
for name, data, bases in zip(group_names, group_datas, group_bases):
	o = 'add("%s", ' % (name)

	for base in bases:
		o += '"%s", ' % base

	for key, val in data.iteritems():
		o += '"%s", %s, ' % (key, val)

	o += ")"

	o = o.replace(", )", ");")

	print o
